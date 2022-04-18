package com.deep.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.common.utils.PageUtils;
import com.deep.common.utils.Query;
import com.deep.coupon.dao.CouponDao;
import com.deep.coupon.model.entity.CouponEntity;
import com.deep.coupon.service.CouponService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 优惠券
 *
 * @author Deep
 * @date 2022/4/16
 */
@Service("couponService")
public class CouponServiceImpl extends ServiceImpl<CouponDao, CouponEntity> implements CouponService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<CouponEntity> wrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (StringUtils.hasLength(key)) {
            wrapper.eq("id", key);
        }
        IPage<CouponEntity> page = this.page(
                new Query<CouponEntity>().getPage(params),
                wrapper
        );
        return new PageUtils(page);
    }
}
