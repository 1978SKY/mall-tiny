package com.deep.order.service.Impl;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.deep.common.exception.BadLoginException;
import com.deep.common.exception.StockException;
import com.deep.common.model.dto.MemberDTO;
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
import com.deep.order.model.enume.GenerateOrderEnum;
import com.deep.order.model.enume.OrderStatusEnum;
import com.deep.order.model.params.OrderSubmitParam;
import com.deep.order.model.vo.OrderConfirmVO;
import com.deep.order.model.vo.OrderVO;
import com.deep.order.service.OrderItemService;
import com.deep.order.service.OrderService;
import com.deep.order.service.OrderWebService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 订单页
 *
 * @author Deep
 * @date 2022/4/5
 */
@Slf4j
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
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ThreadPoolExecutor executor;

    @Override
    public List<OrderVO> queryPage(Map<String, Object> params) {
        MemberDTO member = LoginInterceptor.LOGIN_USER_THREAD_LOCAL.get();

        return orderService.queryUserOrderPage(member.getId(), params);
    }

    @Override
    public OrderConfirmVO confirmOrder() {
        MemberDTO member = LoginInterceptor.LOGIN_USER_THREAD_LOCAL.get();
        if (member == null) {
            throw new BadLoginException("异常登录");
        }
        Long memberId = member.getId();

        OrderConfirmVO confirmVO = new OrderConfirmVO();
        // ① 获取当前请求属性（解决异步编排时请求头共享问题）
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();

        // 远程查询会员收获地址
        CompletableFuture<Void> setAddressFuture = CompletableFuture.runAsync(() -> {
            List<MemberAddressDTO> address = memberFeignService.getMemberAddress(memberId);
            confirmVO.setMemberAddressDTOS(address);
        }, executor);

        // 远程查询购物项
        CompletableFuture<Void> setCartItemsFuture = CompletableFuture.runAsync(() -> {
            RequestContextHolder.setRequestAttributes(attributes);
            List<CartItemDTO> cartItems = cartFeignService.getCheckedItem();
            confirmVO.setItems(cartItems);
        }, executor).thenRunAsync(() -> {
            // 设置库存
            List<Long> skuIds = confirmVO.getItems().stream().map(CartItemDTO::getSkuId).collect(Collectors.toList());
            R r = wareFeignService.isHasStock(skuIds);
            if (r.getCode() == 0) {
                Map<Long, Boolean> stock = r.getData("data", new TypeReference<>() {
                });
                confirmVO.setStocks(stock);
            }
        }, executor);

        // 查询优惠积分
        confirmVO.setIntegration(0);

        // 防重令牌(防止表单重复提交)
        // 为用户设置一个token，三十分钟过期时间（存在redis）
        String token = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set(OrderConstant.USER_ORDER_TOKEN_PREFIX + memberId, token, 30, TimeUnit.MINUTES);
        confirmVO.setOrderToken(token);

        try {
            CompletableFuture.allOf(setAddressFuture, setCartItemsFuture).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return confirmVO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public GenerateOrderEnum submitOrder(OrderSubmitParam submitParam) {
        // 验证防重令牌
        if (!checkOrderToken(submitParam.getOrderToken())) {
            return GenerateOrderEnum.FAILURE_TOKEN;
        }
        // 生成订单
        String orderSn = createOrder(submitParam.getAddrId());
        if (!StringUtils.hasLength(orderSn)) {
            return GenerateOrderEnum.FAILURE_STOCK;
        }
        GenerateOrderEnum successCreate = GenerateOrderEnum.SUCCESS_CREATE;
        successCreate.setMsg(orderSn);
        return successCreate;
    }

    /**
     * 构建订单实体
     *
     * @return 订单号
     */
    private String createOrder(Long addrId) {

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

        // 构建订单项
        return orderItems(order) ? orderSn : null;
    }

    private boolean orderItems(OrderEntity order) {
        // 获取购物车购物项
        List<CartItemDTO> cartItems = cartFeignService.getCheckedItem();

        List<Long> skuIds = new ArrayList<>();
        HashMap<Long, Integer> stockMap = new HashMap<>(cartItems.size());
        BigDecimal totalPrice = new BigDecimal(0);
        for (CartItemDTO item : cartItems) {
            totalPrice = totalPrice.add(item.getTotalPrice());
            skuIds.add(item.getSkuId());
            stockMap.put(item.getSkuId(), item.getCount());
        }

        // 检查并锁定库存
        R r = wareFeignService.checkAndLock(stockMap);
        if (r.getCode() == -1) {
            return false;
        }
        // 支付价格等于总价减去各种优惠价格加上运费...
        order.setTotalAmount(totalPrice);
        // 解决异步编排请求头丢失
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // save order
        CompletableFuture<OrderEntity> orderFuture = CompletableFuture.supplyAsync(() -> {
            orderService.save(order);
            return order;
        }, executor);
        // save item
        CompletableFuture<Void> itemFuture = orderFuture.thenAcceptAsync(res -> {
            List<OrderItemEntity> itemEntities = cartItems.stream().map(item -> {
                OrderItemEntity orderItem = new OrderItemEntity();
                orderItem.setOrderId(res.getId());
                orderItem.setOrderSn(res.getOrderSn());
                orderItem.setSkuId(item.getSkuId());
                orderItem.setSkuPic(item.getImage());
                orderItem.setSkuPrice(item.getPrice());
                orderItem.setSkuQuantity(item.getCount());
                orderItem.setSkuAttrsVals(item.getSkuAttrValues().toString());
                return orderItem;
            }).collect(Collectors.toList());
            orderItemService.saveBatch(itemEntities);
        }, executor);
        // clear cart
        CompletableFuture<Void> cartFuture = CompletableFuture.runAsync(() -> {
            // 异步编排请求头丢失
            RequestContextHolder.setRequestAttributes(requestAttributes);
            cartFeignService.deleteItem(skuIds);
        }, executor);
        // add order to rabbitmq
        CompletableFuture<Void> rabbitFuture = CompletableFuture.runAsync(() -> {
            // 异步编排请求头丢失
            rabbitTemplate.convertAndSend("order-event-exchange", "order.create.order", order);
        }, executor);

        CompletableFuture.allOf(orderFuture, cartFuture, itemFuture, rabbitFuture);
        return true;
    }

    /**
     * 校验订单token合法性
     *
     * @param orderToken 订单唯一值
     * @return 合法性校验结果
     */
    private boolean checkOrderToken(String orderToken) {
        Assert.notNull(orderToken, "订单token不能为空!");
        MemberDTO member = LoginInterceptor.LOGIN_USER_THREAD_LOCAL.get();

        String redisKey = OrderConstant.USER_ORDER_TOKEN_PREFIX + member.getId();
        // 校验并删除orderToken（LUA脚本、原子性操作）
        String script =
                "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Long result = redisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class),
                Collections.singletonList(redisKey), orderToken);
        // 校验成功
        return result != null && result == 1L;
    }
}
