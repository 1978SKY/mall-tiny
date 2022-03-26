package com.deep.product.model.params;

import lombok.Data;

/**
 * 销售属性
 *
 * @author Deep
 * @date 2022/3/25
 */
@Data
public class SaleAttrParam {
    /**
     * 属性id
     */
    private int attrId;
    /**
     * 属性名称
     */
    private String attrName;
    /**
     * 属性值
     */
    private String attrValue;
}
