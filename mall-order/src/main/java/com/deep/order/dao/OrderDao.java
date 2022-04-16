package com.deep.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deep.order.model.entity.OrderEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 *
 * @author Deep
 * @date 2022/3/29
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
}
