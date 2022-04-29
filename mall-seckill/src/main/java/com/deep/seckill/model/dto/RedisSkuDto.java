package com.deep.seckill.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Redis缓存秒杀商品dto
 * 
 * @author Deep
 * @date 2022/4/23
 */
@Data
public class RedisSkuDto {
    // --------------秒杀相关-------------
    /**
     * 活动id
     */
    private Long sessionId;
    /**
     * 场次名称
     */
    private String name;
    /**
     * 每日开始时间
     */
    private Date startTime;
    /**
     * 每日结束时间
     */
    private Date endTime;

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
     * 商品信息
     */
    private SkuInfoDto info;

    /**
     * 商品信息
     */
    @Data
    public static class SkuInfoDto {
        /**
         * 商品名称
         */
        private String skuName;
        /**
         * 原价
         */
        private BigDecimal price;
        /**
         * 商品图片
         */
        private String skuDefaultImg;
        /**
         * 标题
         */
        private String skuTitle;
        /**
         * 副标题
         */
        private String skuSubtitle;
        /**
         * spuId
         */
        private Long spuId;
    }
}
