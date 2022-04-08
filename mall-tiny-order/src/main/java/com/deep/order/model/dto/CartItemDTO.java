package com.deep.order.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 购物项
 *
 * @author Deep
 * @date 2022/4/3
 */
@Data
public class CartItemDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * skuId
     */
    private Long skuId;
    /**
     * 是否被选中（默认被选中）
     */
    private Boolean check = true;
    /**
     * 商品标题
     */
    private String title;
    /**
     * 商品图片
     */
    private String image;
    /**
     * 销售属性
     */
    private List<String> skuAttrValues;
    /**
     * 单价
     */
    private BigDecimal price;
    /**
     * 商品数量
     */
    private Integer count;
    /**
     * 总价
     */
    private BigDecimal totalPrice;

    public BigDecimal getTotalPrice() {
        return this.price.multiply(new BigDecimal("" + this.count));
    }
}
