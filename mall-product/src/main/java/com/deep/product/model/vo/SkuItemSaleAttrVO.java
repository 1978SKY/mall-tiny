package com.deep.product.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * sku销售属性
 *
 * @author Deep
 * @date 2022/3/24
 */
@Data
@Accessors(chain = true)
public class SkuItemSaleAttrVO {
    /**
     * 销售属性id
     */
    private Long attrId;
    /**
     * 销售属性名称
     */
    private String attrName;
    /**
     * 销售属性值
     */
    private List<AttrValueWithSkuIdVO> attrValues;

    @Data
    @Accessors(chain = true)
    @AllArgsConstructor
    public static class AttrValueWithSkuIdVO {
        /**
         * 销售属性值
         */
        private String attrValue;
        /**
         * 商品id（集合转为字符串）
         */
        private String skuIds;
    }
}
