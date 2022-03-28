package com.deep.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deep.common.utils.PageUtils;
import com.deep.ware.model.entity.WareSkuEntity;

import java.util.Map;

/**
 * 商品库存
 *
 * @author Deep
 * @date 2022/3/28
 */
public interface WareSkuService extends IService<WareSkuEntity> {
    /**
     * 获取商品库存列表
     */
    PageUtils queryPage(Map<String, Object> params);
}
