package com.deep.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deep.common.utils.PageUtils;
import com.deep.coupon.model.entity.SpuBoundsEntity;

import java.util.Map;

/**
 * 商品spu积分
 *
 * @author Deep
 * @date 2022/4/16
 */
public interface SpuBoundsService extends IService<SpuBoundsEntity> {
    /**
     * 商品spu积分
     *
     * @param params 查询参数
     * @return 商品spu积分
     */
    PageUtils queryPage(Map<String, Object> params);
}
