package com.deep.common.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户订单dto
 * 
 * @author Deep
 * @date 2022/4/25
 */
@Data
public class OrderDto {
    /**
     * 订单号
     */
    public String orderSn;
    /**
     * 订单创建时间
     */
    private Date createTime;
    /**
     * 订单状态【0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】
     */
    private Integer status;
    /**
     * 应付金额
     */
    private BigDecimal payAmount;
    /**
     * 支付时间
     */
    private Date paymentTime;
    /**
     * 发货时间
     */
    private Date deliveryTime;

    public String getOrderStatus() {
        return switch (status) {
            case 0 -> "待付款";
            case 1 -> "待发货";
            case 2 -> "已发货";
            case 3 -> "已完成";
            case 4 -> "已关闭";
            default -> "无效订单";
        };
    }
}
