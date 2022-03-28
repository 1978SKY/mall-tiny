package com.deep.product.model.params;

import com.deep.common.convert.InputConverter;
import com.deep.product.model.entity.SkuInfoEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品信息
 *
 * @author Deep
 * @date 2022/3/25
 */
@Data
public class SkuParam implements InputConverter<SkuInfoEntity> {
    /**
     * 销售属性
     */
    private List<SaleAttrParam> attr;
    /**
     * sku name
     */
    private String skuName;
    /**
     * 商品价格
     */
    private BigDecimal price;
    /**
     * 商品标题
     */
    private String skuTitle;
    /**
     * 子标题
     */
    private String skuSubtitle;
    /**
     * 商品图集
     */
    private List<SkuImageParam> images;
    /**
     * 销售属性组合值（如：["星耀黑","6G"]）
     */
    private List<String> descar;
    /**
     * 满减数量（商品数量达到fullCount时进行满减）
     */
    private int fullCount;
    /**
     * 折扣
     */
    private BigDecimal discount;
    private int countStatus;
    /**
     * 到达该价格时进行满减
     */
    private BigDecimal fullPrice;
    /**
     * 满减价格
     */
    private BigDecimal reducePrice;
    private int priceStatus;
    /**
     * 会员价格
     */
    private List<MemberPriceParam> memberPrice;

    /**
     * 会员价格
     */
    @Data
    public static class MemberPriceParam {
        /**
         * 商品id
         */
        private Long id;
        /**
         * 商品名
         */
        private String name;
        /**
         * 商品价格
         */
        private BigDecimal price;
    }
}
