package com.deep.order.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.common.utils.BeanUtils;
import com.deep.common.utils.PageUtils;
import com.deep.common.utils.Query;
import com.deep.order.dao.OrderDao;
import com.deep.order.model.entity.OrderEntity;
import com.deep.order.model.entity.OrderItemEntity;
import com.deep.order.model.enume.OrderStatusEnum;
import com.deep.order.model.vo.OrderItemVO;
import com.deep.order.model.vo.OrderVO;
import com.deep.order.model.vo.PayVO;
import com.deep.order.service.OrderItemService;
import com.deep.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 订单
 *
 * @author Deep
 * @date 2022/3/29
 */
@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    private final OrderItemService orderItemService;

    public OrderServiceImpl(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<OrderEntity> wrapper = new QueryWrapper<>();
        String key = (String)params.get("key");
        if (StringUtils.hasLength(key)) {
            wrapper.eq("order_sn", key).or().eq("member_id", key).or().like("member_username", key);
        }
        IPage<OrderEntity> page = this.page(new Query<OrderEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }

    @Override
    public List<OrderVO> queryUserOrderPage(@NonNull Long userId, Map<String, Object> params) {
        Assert.notNull(userId, "用户id不能为空!");

        QueryWrapper<OrderEntity> wrapper = new QueryWrapper<>();
        IPage<OrderEntity> page = this.page(new Query<OrderEntity>().getPage(params),
            wrapper.eq("member_id", userId).orderByDesc("create_time"));
        List<OrderEntity> orders = page.getRecords();
        List<Long> orderIds = orders.stream().map(OrderEntity::getId).collect(Collectors.toList());

        List<OrderItemEntity> items = orderItemService.getByOrderIds(orderIds);
        List<OrderItemVO> itemVOS = BeanUtils.transformFromInBatch(items, OrderItemVO.class);
        // key:orderId value:List<OrderVO>
        Map<Long, List<OrderItemVO>> itemsMap = new HashMap<>();
        for (OrderItemVO itemVO : itemVOS) {
            List<OrderItemVO> list;
            if (!itemsMap.containsKey(itemVO.getOrderId())) {
                list = new ArrayList<>();
            } else {
                list = itemsMap.get(itemVO.getOrderId());
            }
            list.add(itemVO);
            itemsMap.put(itemVO.getOrderId(), list);
        }

        return orders.stream().map((order) -> {
            OrderVO orderVO = BeanUtils.transformFrom(order, OrderVO.class);
            assert orderVO != null;
            orderVO.setStatus(OrderStatusEnum.checkStatus(order.getStatus()));
            orderVO.setItems(itemsMap.get(orderVO.getId()));
            return orderVO;
        }).collect(Collectors.toList());
    }

    @Override
    public PayVO getOrderPay(@NonNull String orderSn) {
        Assert.hasLength(orderSn, "订单号不能为空!");

        PayVO payVO = new PayVO();
        QueryWrapper<OrderEntity> wrapper = new QueryWrapper<>();
        OrderEntity order = getOne(wrapper.eq("order_sn", orderSn));
        BigDecimal payAmount = order.getPayAmount().setScale(2, BigDecimal.ROUND_UP);
        payVO.setTotal_amount(payAmount.toString());
        payVO.setOut_trade_no(order.getOrderSn());
        payVO.setSubject(order.getOrderSn());
        payVO.setBody("");

        return payVO;
    }

    @Override
    public OrderVO getOrderVO(String orderSn) {
        Assert.hasLength(orderSn, "订单号不能为空!");

        QueryWrapper<OrderEntity> wrapper = new QueryWrapper<>();
        OrderEntity order = getOne(wrapper.eq("order_sn", orderSn));
        OrderVO orderVO = BeanUtils.transformFrom(order, OrderVO.class);
        List<OrderItemEntity> items = orderItemService.getByOrderIds(Collections.singletonList(order.getId()));
        List<OrderItemVO> itemVos = BeanUtils.transformFromInBatch(items, OrderItemVO.class);
        assert orderVO != null;
        orderVO.setItems(itemVos);
        return orderVO;
    }

    @Override
    public List<OrderEntity> getMemberOrders(@NonNull Long memberId) {
        Assert.notNull(memberId, "会员id不能为空!");

        QueryWrapper<OrderEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id", memberId);
        return list(wrapper);
    }
}
