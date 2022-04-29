package com.deep.product.service;

import com.deep.common.utils.PageUtils;
import com.deep.product.model.entity.SkuInfoEntity;
import com.deep.product.model.vo.ProductVo;

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

    /**
     * 获取热销商品
     * 
     * @param count 数量
     * @return 热销商品
     */
    List<ProductVo> getTrendingProduct(int count);

    /**
     * 获取精选商品
     * 
     * @param pageCount 页数
     * @param count 每页数量
     * @return 精选商品
     */
    List<List<ProductVo>> getSelectProduct(int pageCount, int count);

    /***
     * 每周最佳销售
     * 
     * @param count 数量
     * @return 每周最佳销售商品
     */
    List<ProductVo> getBestSaleEveryWeek(int count);
}
