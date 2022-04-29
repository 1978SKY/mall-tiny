package com.deep.seckill.model.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 秒杀商品详细信息
 * 
 * @author Deep
 * @date 2022/4/22
 */
@Data
public class SeckillSkuVo {
    // ----------------商品信息-----------------
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
    // --------------活动相关信息----------------
    /**
     * 活动id
     */
    private Long sessionId;
    /**
     * 场次名称
     */
    private String name;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
}
