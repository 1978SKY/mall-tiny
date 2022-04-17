package com.deep.seckill.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 每日秒杀
 *
 * @author deep
 * @email ${email}
 * @date 2022-01-13 17:28:13
 */
@Data
@TableName("sms_seckill_session")
public class SeckillSessionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 场次名称
     */
    private String name;
    /**
     * 所属秒杀活动id
     */
    private Long promotionId;
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
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(exist = false)
    private List<SeckillSkuRelationEntity> relationSkus;
}
