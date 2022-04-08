package com.deep.order.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.order.dao.OrderItemDao;
import com.deep.order.model.entity.OrderItemEntity;
import com.deep.order.service.OrderItemService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 订单项信息
 */
@Service("orderItemService")
public class OrderItemServiceImpl extends ServiceImpl<OrderItemDao, OrderItemEntity> implements OrderItemService {

    @Override
    public List<OrderItemEntity> getByOrderIds(List<Long> orderIds) {
        Assert.notEmpty(orderIds, "订单号id集合不能为空!");

        QueryWrapper<OrderItemEntity> wrapper = new QueryWrapper<>();
        for (Long orderId : orderIds) {
            wrapper.eq("order_id", orderId).or();
        }
        return list(wrapper);
    }

}