package com.deep.order.model.params;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单提交
 *
 * @author Deep
 * @date 2022/4/6
 */
@Data
public class OrderSubmitParam {
    // TODO合法性校验
    /**
     * 收获地址的id
     **/
    private Long addrId;
    /**
     * 支付方式
     **/
    private Integer payType;
    /**
     * 应付价格
     **/
    private BigDecimal payPrice;
    /**
     * 防重令牌
     **/
    private String orderToken;
    /**
     * 订单备注
     **/
    private String remarks;
}
