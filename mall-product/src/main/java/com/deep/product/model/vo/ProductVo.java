package com.deep.product.model.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 首页商品vo
 * 
 * @author Deep
 * @date 2022/4/24
 */
@Data
public class ProductVo {
    /**
     * 商品id
     */
    private Long skuId;
    /**
     * sku名称
     */
    private String skuName;
    /**
     * 默认图片
     */
    private String skuDefaultImg;
    /**
     * 标题
     */
    private String skuTitle;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 销量
     */
    private Long saleCount;
    /**
     * 是否有货
     */
    private boolean hasStock = true;
}
