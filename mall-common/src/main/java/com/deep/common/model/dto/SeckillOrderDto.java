package com.deep.common.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 秒杀活动订单dto
 *
 * @author Deep
 * @date 2022/5/3
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeckillOrderDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 订单号
     */
    private String orderSn;

    /**
     * 活动场次id
     */
    private Long sessionId;
    /**
     * 商品id
     */
    private Long skuId;
    /**
     * 秒杀价格
     */
    private BigDecimal seckillPrice;

    /**
     * 购买数量
     */
    private Integer count;

    /**
     * 会员ID
     */
    private Long memberId;
}
