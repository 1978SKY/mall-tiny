package com.deep.product.model.vo;

import com.deep.product.model.dto.SeckillSkuDTO;
import com.deep.product.model.entity.SkuImagesEntity;
import com.deep.product.model.entity.SkuInfoEntity;
import com.deep.product.model.entity.SpuInfoDescEntity;
import lombok.Data;

import java.util.List;

/**
 * 商品详情vo
 *
 * @author Deep
 * @date 2022/3/24
 */
@Data
public class SkuItemVo {
    /**
     * sku基本信息
     */
    private SkuInfoEntity info;
    /**
     * 是否有货
     */
    private boolean hasStock = true;
    /**
     * spu销售属性
     */
    private List<SkuItemSaleAttrVO> saleAttr;
    /**
     * 属性分组
     */
    private List<SpuItemAttrGroupVO> groupAttrs;
    /**
     * sku图片信息
     */
    private List<SkuImagesEntity> images;
    /**
     * spu信息介绍
     */
    private SpuInfoDescEntity desc;
    /**
     * 秒杀信息
     */
    private SeckillSkuDTO seckillSkuDTO;
}
