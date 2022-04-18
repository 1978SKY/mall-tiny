package com.deep.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deep.common.utils.PageUtils;
import com.deep.coupon.model.entity.SeckillPromotionEntity;

import java.util.Map;

/**
 * 秒杀活动
 *
 * @author Deep
 * @date 2022/4/16
 */
public interface SeckillPromotionService extends IService<SeckillPromotionEntity> {
    /**
     * 秒杀活动
     *
     * @param params 查询参数
     * @return 秒杀活动
     */
    PageUtils queryPage(Map<String, Object> params);
}
