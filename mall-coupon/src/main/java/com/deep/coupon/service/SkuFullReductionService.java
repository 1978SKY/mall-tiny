package com.deep.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deep.common.utils.PageUtils;
import com.deep.coupon.model.entity.SkuFullReductionEntity;

import java.util.Map;

/**
 * 商品满减信息
 *
 * @author Deep
 * @date 2022/4/16
 */
public interface SkuFullReductionService extends IService<SkuFullReductionEntity> {
    /**
     * 获取商品满减信息
     *
     * @param params 查询参数
     * @return 商品满减信息
     */
    PageUtils queryPage(Map<String, Object> params);
}
