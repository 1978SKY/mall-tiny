package com.deep.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deep.common.utils.PageUtils;
import com.deep.product.model.entity.SkuInfoEntity;

import java.util.Map;

/**
 * sku信息
 *
 * @author Deep
 * @date 2022/3/20
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {
    /**
     * 分页查询
     */
    PageUtils queryPage(Map<String, Object> params);
}
