package com.deep.coupon.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deep.coupon.model.entity.CouponSpuRelationEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券与产品关联
 * 
 * @author deep
 * @email ${email}
 * @date 2022-01-13 17:28:13
 */
@Mapper
public interface CouponSpuRelationDao extends BaseMapper<CouponSpuRelationEntity> {
	
}
