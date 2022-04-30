package com.deep.common.model.dto;

import lombok.Data;

/**
 * 库存工作单
 *
 * @author Deep
 * @date 2022/4/29
 */
@Data
public class OrderTaskDetailDto {
    private Long id;
    /**
     * sku_id
     */
    private Long skuId;
    /**
     * sku_name
     */
    private String skuName;
    /**
     * 购买个数
     */
    private Integer skuNum;
    /**
     * 工作单id
     */
    private Long taskId;
    /**
     * 仓库id
     */
    private Long wareId;
    /**
     * 锁定状态
     * 1-锁定 2-解锁 3-扣减
     */
    private Integer lockStatus;
    /**
     * 订单号
     */
    private String orderSn;
}
