package com.deep.seckill.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deep.seckill.model.entity.CouponEntity;
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
