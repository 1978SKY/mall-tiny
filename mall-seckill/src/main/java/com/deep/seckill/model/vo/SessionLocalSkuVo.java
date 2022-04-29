package com.deep.seckill.model.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 商品秒杀统一封装
 * 
 * @author Deep
 * @date 2022/4/21
 */
@Data
public class SessionLocalSkuVo {
    /**
     * 活动id
     */
    private Long id;
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
     * 启用状态
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 商品
     */
    private List<LocalSkuVo> localVos;

    @Data
    public static class LocalSkuVo {
        /**
         * 商品id
         */
        private Long skuId;
        /**
         * 活动id
         */
        private Long sessionId;
        /**
         * 商品名
         */
        private String skuName;
        /**
         * 商品标题
         */
        private String skuTitle;
        /**
         * 秒杀价格
         */
        private BigDecimal seckillPrice;
        /**
         * 价格
         */
        private BigDecimal price;
        /**
         * 默认图片
         */
        private String skuDefaultImg;
        /**
         * 销量
         */
        private Integer saleCount;
    }
}
