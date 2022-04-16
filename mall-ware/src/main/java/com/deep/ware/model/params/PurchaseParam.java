package com.deep.ware.model.params;

import com.deep.common.convert.InputConverter;
import com.deep.ware.model.entity.PurchaseEntity;
import lombok.Data;

/**
 * 订单参数
 *
 * @author Deep
 * @date 2022/3/28
 */
@Data
public class PurchaseParam implements InputConverter<PurchaseEntity> {

    /**
     * 订单id
     */
    private Long id;
    /**
     * 优先级
     */
    private String priority;
    /**
     * 分配员id
     */
    private Long assigneeId;
    /**
     * 分配员名称
     */
    private String assigneeName;
    /**
     * 分配员手机号
     */
    private String phone;
    /**
     * 订单状态
     */
    private Integer status;
}
