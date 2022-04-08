package com.deep.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deep.order.model.entity.OrderItemEntity;

import java.util.List;

/**
 * 订单项信息
 *
 * @author deep
 * @email ${email}
 * @date 2022-01-13 17:13:27
 */
public interface OrderItemService extends IService<OrderItemEntity> {

    /**
     * 通过订单号id获取订单项
     *
     * @param orderIds 订单项id集合
     * @return 订单项集合
     */
    List<OrderItemEntity> getByOrderIds(List<Long> orderIds);
}

