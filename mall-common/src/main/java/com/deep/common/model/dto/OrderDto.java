package com.deep.common.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
        String res;
        switch (status) {
            case 1:
                res = "待发货";
                break;
            case 2:
                res = "已发货";
                break;
            case 3:
                res = "已完成";
                break;
            case 4:
                res = "已关闭";
                break;
            default:
                res = "无效订单";
        }
        return res;
    }
}
