package com.deep.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deep.common.utils.PageUtils;
import com.deep.coupon.model.entity.CouponEntity;

import java.util.Map;

/**
 * 优惠券
 *
 * @author Deep
 * @date 2022/4/16
 */
public interface CouponService extends IService<CouponEntity> {

    /**
     * 获取优惠券
     *
     * @param params 查询参数
     * @return 优惠券
     */
    PageUtils queryPage(Map<String, Object> params);
}
