package com.deep.seckill.model.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 秒杀商品vo
 * 
 * @author Deep
 * @date 2022/4/21
 */
@Data
public class SkuInfoVO {
    /**
     * 商品id
     */
    private Long skuId;
    /**
     * sku名称
     */
    private String skuName;
    /**
     * sku介绍描述
     */
    private String skuDesc;
    /**
     * 品牌id
     */
    private Long brandId;
    /**
     * 默认图片
     */
    private String skuDefaultImg;
    /**
     * 标题
     */
    private String skuTitle;
    /**
     * 副标题
     */
    private String skuSubtitle;
    /**
     * 原价
     */
    private BigDecimal price;
}
