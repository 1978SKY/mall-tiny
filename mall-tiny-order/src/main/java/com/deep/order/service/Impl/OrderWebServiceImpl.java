package com.deep.order.service.Impl;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.deep.common.exception.BadLoginException;
import com.deep.common.model.dto.MemberDTO;
import com.deep.common.utils.BeanUtils;
import com.deep.common.utils.R;
import com.deep.order.feign.CartFeignService;
import com.deep.order.feign.MemberFeignService;
import com.deep.order.feign.WareFeignService;
import com.deep.order.interceptor.LoginInterceptor;
import com.deep.order.model.constant.OrderConstant;
import com.deep.order.model.dto.CartItemDTO;
import com.deep.order.model.dto.MemberAddressDTO;
import com.deep.order.model.entity.OrderEntity;
import com.deep.order.model.entity.OrderItemEntity;
import com.deep.order.model.enume.OrderStatusEnum;
import com.deep.order.model.params.OrderSubmitParam;
import com.deep.order.model.vo.OrderConfirmVO;
import com.deep.order.model.vo.OrderItemVO;
import com.deep.order.model.vo.OrderVO;
import com.deep.order.service.OrderItemService;
import com.deep.order.service.OrderService;
import com.deep.order.service.OrderWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 订单页
 *
 * @author Deep
 * @date 2022/4/5
 */
@Service("orderWebService")
public class OrderWebServiceImpl implements OrderWebService {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private MemberFeignService memberFeignService;
    @Autowired
    private CartFeignService cartFeignService;
    @Autowired
    private WareFeignService wareFeignService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public List<OrderVO> queryPage(Map<String, Object> params) {
        MemberDTO member = LoginInterceptor.LOGIN_USER_THREAD_LOCAL.get();

        return orderService.queryUserOrderPage(member.getId(), params);
    }

    @Override
    public OrderConfirmVO confirmOrder() {
        MemberDTO member = LoginInterceptor.LOGIN_USER_THREAD_LOCAL.get();
        Long memberId = member.getId();
        if (memberId == null) {
            throw new BadLoginException("异常登录");
        }
        OrderConfirmVO confirmVO = new OrderConfirmVO();
        // ① 获取当前请求属性（解决异步编排时请求头共享问题）
        // RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        // RequestContextHolder.setRequestAttributes(attributes);
        // 远程查询会员收获地址
        List<MemberAddressDTO> address = memberFeignService.getMemberAddress(memberId);
        confirmVO.setMemberAddressDTOS(address);

        // 远程查询购物项
        List<CartItemDTO> cartItems = cartFeignService.getCheckedItem();
        confirmVO.setItems(cartItems);

        // 设置库存
        List<Long> skuIds = cartItems.stream()
                .map(CartItemDTO::getSkuId).collect(Collectors.toList());
        R r = wareFeignService.isHasStock(skuIds);
        if (r.getCode() == 0) {
            Map<Long, Boolean> stock = r.getData("data", new TypeReference<>() {
            });
            confirmVO.setStocks(stock);
        }
        // 查询优惠积分
        confirmVO.setIntegration(0);

        // 防重令牌(防止表单重复提交)
        // 为用户设置一个token，三十分钟过期时间（存在redis）
        String token = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue()
                .set(OrderConstant.USER_ORDER_TOKEN_PREFIX + memberId, token, 30, TimeUnit.MINUTES);
        confirmVO.setOrderToken(token);

        return confirmVO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<Integer, OrderVO> submitOrder(OrderSubmitParam submitParam) {
        MemberDTO member = LoginInterceptor.LOGIN_USER_THREAD_LOCAL.get();
        HashMap<Integer, OrderVO> resultMap = new HashMap<>();
        // 防重令牌
        String orderToken = submitParam.getOrderToken();
        if (!checkOrderToken(member.getId(), orderToken)) {
            resultMap.put(-1, null);
            return resultMap;
        }
        // 生成订单
        OrderVO orderVO = createOrder(submitParam.getAddrId());
        resultMap.put(1, orderVO);

        // 锁定库存
        // 清除购物车

        return resultMap;
    }

    /**
     * 生成订单
     *
     * @return 订单实体
     */
    private OrderVO createOrder(Long addrId) {
        MemberDTO member = LoginInterceptor.LOGIN_USER_THREAD_LOCAL.get();
        // 订单号
        String orderSn = IdWorker.getTimeId();
        // 构建订单数据
        OrderEntity order = new OrderEntity();
        order.setMemberId(member.getId());
        order.setMemberUsername(member.getUsername());
        order.setOrderSn(orderSn);
        // set address
        MemberAddressDTO address = memberFeignService.getMajorAddress(addrId);
        order.setReceiverName(address.getName());
        order.setReceiverPhone(address.getPhone());
        order.setReceiverPostCode(address.getPostCode());
        order.setReceiverProvince(address.getProvince());
        order.setReceiverCity(address.getCity());
        order.setReceiverRegion(address.getRegion());
        order.setReceiverDetailAddress(address.getDetailAddress());
        // set fare
        order.setFreightAmount(new BigDecimal(10L));
        // set status
        order.setStatus(OrderStatusEnum.CREATE_NEW.getCode());
        order.setAutoConfirmDay(7);
        order.setConfirmStatus(0);

        //TODO get item form db

        List<CartItemDTO> cartItems = cartFeignService.getCheckedItem();
        BigDecimal totalPrice = new BigDecimal(0);
        for (CartItemDTO cartItem : cartItems) {
            totalPrice = totalPrice.add(cartItem.getTotalPrice());
        }
        order.setTotalAmount(totalPrice);
        //TODO 支付价格等于总价减去各种优惠价格加上运费
        order.setPayAmount(totalPrice);
        orderService.save(order);

        // save item
        List<OrderItemEntity> itemEntities = cartItems.stream().map(item -> {
            OrderItemEntity itemEntity = new OrderItemEntity();
            itemEntity.setOrderId(order.getId());
            itemEntity.setOrderSn(order.getOrderSn());
            itemEntity.setSkuId(item.getSkuId());
            itemEntity.setSkuPic(item.getImage());
            itemEntity.setSkuPrice(item.getPrice());
            itemEntity.setSkuQuantity(item.getCount());
            itemEntity.setSkuAttrsVals("");
            return itemEntity;
        }).collect(Collectors.toList());
        orderItemService.saveBatch(itemEntities);

        OrderVO orderVO = BeanUtils.transformFrom(order, OrderVO.class);
        List<OrderItemVO> itemVOS = BeanUtils.transformFromInBatch(itemEntities, OrderItemVO.class);
        Map<Long, String> titleMap = new HashMap<>();
        for (CartItemDTO cartItem : cartItems) {
            titleMap.putIfAbsent(cartItem.getSkuId(), cartItem.getTitle());
        }
        itemVOS.forEach(item -> {
            item.setTitle(titleMap.get(item.getSkuId()));
        });
        assert orderVO != null;
        orderVO.setItems(itemVOS);

        return orderVO;
    }

    /**
     * 校验订单token合法性
     *
     * @param orderToken 订单唯一值
     * @return 合法性校验结果
     */
    private boolean checkOrderToken(Long memberId, String orderToken) {
        Assert.notNull(orderToken, "订单id不能为空!");
        String redisKey = OrderConstant.USER_ORDER_TOKEN_PREFIX + memberId;
        // 校验并删除orderToken（LUA脚本、原子性操作）
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Long result = redisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class),
                Collections.singletonList(redisKey), orderToken);
        if (result != null && result == 1L) {
            // 校验成功
            return true;
        }
        return false;
    }
}
