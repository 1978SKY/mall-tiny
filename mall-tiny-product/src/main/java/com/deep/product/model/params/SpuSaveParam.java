package com.deep.product.model.params;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品spu传输
 *
 * @author Deep
 * @date 2022/3/25
 */
@Data
public class SpuSaveParam {
    /**
     * 商品名
     */
    private String spuName;
    /**
     * 商品描述
     */
    private String spuDescription;
    /**
     * 分类id
     */
    private Long catalogId;
    /**
     * 品牌id
     */
    private Long brandId;
    /**
     * 重量（kg）
     */
    private BigDecimal weight;
    /**
     * 发布状态[0 - 下架，1 - 上架]
     */
    private int publishStatus;
    /**
     * 商品副标题（链接地址）
     */
    private List<String> decript;
    /**
     * 宣传图片地址
     */
    private List<String> images;
    /**
     * 积分、成长值
     */
    private BoundsParam bounds;
    /**
     * 基础属性
     */
    private List<SpuAttrParam> baseAttrs;
    /**
     * sku（比如手机：8+256黑色为一个sku、8+256白色为一个sku）
     */
    private List<SkuParam> skus;

    /**
     * 积分成长值
     */
    @Data
    public static class BoundsParam {
        /**
         * 积分
         */
        private BigDecimal buyBounds;
        /**
         * 成长值
         */
        private BigDecimal growBounds;
    }

    /**
     * spu属性信息
     */
    @Data
    public static class SpuAttrParam {
        /**
         * 属性id
         */
        private Long attrId;
        /**
         * 属性值
         */
        private String attrValues;
        /**
         * 快速展示
         */
        private int showDesc;
    }
}
