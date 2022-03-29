package com.deep.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deep.common.utils.PageUtils;
import com.deep.order.model.entity.OrderEntity;

import java.util.Map;

/**
 * 订单
 *
 * @author Deep
 * @date 2022/3/29
 */
public interface OrderService extends IService<OrderEntity> {
    /**
     * 获取订单列表
     */
    PageUtils queryPage(Map<String, Object> params);
}
