package com.deep.order.model.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单项
 *
 * @author Deep
 * @date 2022/4/7
 */
@Data
public class OrderItemVO {
    /**
     * id
     */
    private Long id;
    /**
     * order_id
     */
    private Long orderId;
    /**
     * order_sn
     */
    private String orderSn;
    /**
     * 商品sku编号
     */
    private Long skuId;
    /**
     * 商品sku标题
     */
    private String title;
    /**
     * 商品sku图片
     */
    private String skuPic;
    /**
     * 商品sku价格
     */
    private BigDecimal skuPrice;
    /**
     * 商品购买的数量
     */
    private Integer skuQuantity;
    /**
     * 商品销售属性组合（JSON）
     */
    private String skuAttrsVals;

    public BigDecimal getSubTotal() {
        return skuPrice.multiply(new BigDecimal(skuQuantity));
    }
}
