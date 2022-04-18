package com.deep.coupon.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deep.coupon.model.entity.CouponSpuCategoryRelationEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券分类关联
 * 
 * @author deep
 * @email ${email}
 * @date 2022-01-13 17:28:13
 */
@Mapper
public interface CouponSpuCategoryRelationDao extends BaseMapper<CouponSpuCategoryRelationEntity> {
	
}
