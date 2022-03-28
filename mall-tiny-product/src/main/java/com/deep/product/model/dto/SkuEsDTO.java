package com.deep.product.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * sku检索模型(用保存到于ES)
 *
 * @author Deep
 * @date 2022/3/27
 */
@Data
public class SkuEsDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * sku id
     */
    private Long skuId;
    /**
     * spu id
     */
    private Long spuId;
    /**
     * sku标题
     */
    private String skuTitle;
    /**
     * sku价格
     */
    private BigDecimal skuPrice;
    /**
     * sku图片路径
     */
    private String skuImg;
    /**
     * 销售数量
     */
    private Long saleCount;
    /**
     * 是否有货
     */
    private Boolean hasStock;
    /**
     * 热门分数
     */
    private Long hotScore;
    /**
     * 品牌id
     */
    private Long brandId;
    /**
     * 分类id
     */
    private Long catalogId;
    /**
     * 品牌名称
     */
    private String brandName;
    /**
     * 品牌路径
     */
    private String brandImg;
    /**
     * 分类名称
     */
    private String catalogName;
    /**
     * 基础属性
     */
    private List<BaseAttr> attrs;

    /**
     * 基础属性
     */
    @AllArgsConstructor
    @Data
    public static class BaseAttr implements Serializable {
        /**
         * 属性id
         */
        private Long attrId;
        /**
         * 属性名称
         */
        private String attrName;
        /**
         * 属性值
         */
        private String attrValue;
    }
}
