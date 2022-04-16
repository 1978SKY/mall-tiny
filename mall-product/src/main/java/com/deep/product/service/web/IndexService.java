package com.deep.product.service.web;

import com.deep.common.utils.PageUtils;
import com.deep.product.model.entity.SkuInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * 商城首页
 *
 * @author Deep
 * @date 2022/4/8
 */
public interface IndexService {
    /**
     * 获取商品分页
     */
    List<SkuInfoEntity> getProducts();
}
