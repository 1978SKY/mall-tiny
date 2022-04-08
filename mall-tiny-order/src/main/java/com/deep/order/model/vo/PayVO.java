package com.deep.order.model.vo;

import lombok.Data;

/**
 * pay
 *
 * @author Deep
 * @date 2022/4/7
 */
@Data
public class PayVO {
    /**
     * 商户订单号 必填
     */
    private String out_trade_no;
    /**
     * 订单名称 必填
     */
    private String subject;
    /**
     * 付款金额 必填
     */
    private String total_amount;
    /**
     * 商品描述 可空
     */
    private String body;
}
