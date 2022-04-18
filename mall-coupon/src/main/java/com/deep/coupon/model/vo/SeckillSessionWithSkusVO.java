package com.deep.coupon.model.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 秒杀场次
 *
 * @author Deep
 * @date 2022/4/18
 */
@Data
public class SeckillSessionWithSkusVO {
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
     * 秒杀商品集合
     */
    private List<SeckillSkuVO> relationSkus;
}
