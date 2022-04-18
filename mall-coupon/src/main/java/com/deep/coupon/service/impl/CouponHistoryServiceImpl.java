package com.deep.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.common.utils.PageUtils;
import com.deep.common.utils.Query;
import com.deep.coupon.dao.CouponHistoryDao;
import com.deep.coupon.model.entity.CouponHistoryEntity;
import com.deep.coupon.service.CouponHistoryService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 优惠券发放历史记录
 *
 * @author Deep
 * @date 2022/4/16
 */
@Service("couponHistoryService")
public class CouponHistoryServiceImpl
        extends ServiceImpl<CouponHistoryDao, CouponHistoryEntity> implements CouponHistoryService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<CouponHistoryEntity> wrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (StringUtils.hasLength(key)) {
            wrapper.eq("id", key).or()
                    .eq("coupon_id", key).or()
                    .eq("coupon_id", key).or()
                    .eq("member_id", key).or()
                    .eq("order_sn", key);
        }
        IPage<CouponHistoryEntity> page = this.page(new Query<CouponHistoryEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }

}
