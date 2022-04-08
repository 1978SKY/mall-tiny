package com.deep.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deep.order.model.entity.OrderItemEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单项信息
 * 
 * @author deep
 * @email ${email}
 * @date 2022-01-13 17:13:27
 */
@Mapper
public interface OrderItemDao extends BaseMapper<OrderItemEntity> {
	
}
