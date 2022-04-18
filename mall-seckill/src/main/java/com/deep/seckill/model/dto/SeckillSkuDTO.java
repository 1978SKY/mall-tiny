package com.deep.seckill.model.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 秒杀场次关联商品
 *
 * @author Deep
 * @date 2022/4/18
 */
@Data
public class SeckillSkuDTO {
    /**
     * 关联id
     */
    private Long id;
    /**
     * 活动场次id
     */
    private Long promotionSessionId;
    /**
     * 商品id
     */
    private Long skuId;
    /**
     * 秒杀价格
     */
    private BigDecimal seckillPrice;
    /**
     * 秒杀总量
     */
    private Integer seckillCount;
    /**
     * 每人限购数量
     */
    private Integer seckillLimit;
    /**
     * 排序
     */
    private Integer seckillSort;
}
