package com.deep.product.service;

import java.util.List;
import java.util.Map;

import org.springframework.lang.NonNull;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deep.common.utils.PageUtils;
import com.deep.product.model.entity.SkuInfoEntity;
import com.deep.product.model.params.SpuSaveParam;
import com.deep.product.model.vo.SkuItemVo;

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

    /**
     * 获取商品项详细信息
     *
     * @param skuId 商品id
     * @return 商品详细信息
     */
    SkuItemVo queryItem(@NonNull Long skuId);

    /**
     * 保存skus
     */
    void savSkuParams(@NonNull Long spuId, @NonNull SpuSaveParam spuSaveParam);

    /**
     * 获取sku销售属性
     */
    List<String> getSkuSaleAttr(@NonNull Long skuId);

    /**
     * 获取最佳销售
     * 
     * @param count 销售数量
     * @return 商品集合
     */
    List<SkuInfoEntity> getBestSale(int count);

}
