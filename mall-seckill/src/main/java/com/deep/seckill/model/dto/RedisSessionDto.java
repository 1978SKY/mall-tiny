package com.deep.seckill.model.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Redis缓存秒杀活动dto
 * 
 * @author Deep
 * @date 2022/4/23
 */
@Data
public class RedisSessionDto {
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
     * 商品id集合
     */
    private List<Long> skuIds;
}
