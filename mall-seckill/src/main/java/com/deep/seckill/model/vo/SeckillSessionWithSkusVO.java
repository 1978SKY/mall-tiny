package com.deep.seckill.model.vo;

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
     * 商品
     */
    private List<SeckillSkuVO> skuInfoVOList;
}
