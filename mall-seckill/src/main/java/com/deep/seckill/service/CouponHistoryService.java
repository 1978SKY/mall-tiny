package com.deep.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deep.common.utils.PageUtils;
import com.deep.seckill.model.entity.CouponHistoryEntity;

import java.util.Map;

/**
 * 优惠券发放历史记录
 *
 * @author Deep
 * @date 2022/4/16
 */
public interface CouponHistoryService extends IService<CouponHistoryEntity> {
    /**
     * 获取优惠券发放历史记录
     *
     * @param params 查询参数
     * @return 发放历史记录
     */
    PageUtils queryPage(Map<String, Object> params);
}
