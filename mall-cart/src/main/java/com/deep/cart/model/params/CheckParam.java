package com.deep.cart.model.params;

import lombok.Data;

/**
 * 商品切换选中状态
 *
 * @author Deep
 * @date 2022/4/10
 */
@Data
public class CheckParam {
    /**
     * 商品id
     */
    private Long skuId;
    /**
     * 是否被选中
     */
    private Boolean checked;
}
