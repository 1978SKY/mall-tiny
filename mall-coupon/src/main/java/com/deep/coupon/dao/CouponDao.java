package com.deep.coupon.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deep.coupon.model.entity.CouponEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 *
 * @author deep
 * @email ${email}
 * @date 2022-01-13 17:28:13
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {

}
