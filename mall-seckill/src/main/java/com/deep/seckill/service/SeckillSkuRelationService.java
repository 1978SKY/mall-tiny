package com.deep.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deep.common.utils.PageUtils;
import com.deep.seckill.model.entity.SeckillSkuRelationEntity;

import java.util.Map;

/**
 * 每日秒杀&商品关联
 *
 * @author Deep
 * @date 2022/4/17
 */
public interface SeckillSkuRelationService extends IService<SeckillSkuRelationEntity> {

    /**
     * 查询秒杀活动商品
     *
     * @param params 查询参数
     * @return 秒杀活动商品
     */
    PageUtils queryPage(Map<String, Object> params);
}
