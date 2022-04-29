package com.deep.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deep.product.model.entity.SkuSaleAttrValueEntity;
import com.deep.product.model.vo.SkuItemSaleAttrVO;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * sku销售属性
 *
 * @author Deep
 * @date 2022/3/24
 */
public interface SkuSaleAttrValueService extends IService<SkuSaleAttrValueEntity> {
    /**
     * 获取销售属性
     * 
     * @param spuId spuId
     * @return 商品销售属性集合
     */
    List<SkuItemSaleAttrVO> getSaleAttrsBySpuId(@NonNull Long spuId);

    /**
     * 获取销售属性
     *
     * @param skuId 商品id
     * @return 销售属性集合
     */
    List<SkuSaleAttrValueEntity> getSaleAttrsBySkuId(@NonNull Long skuId);
}
