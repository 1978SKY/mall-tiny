package com.deep.seckill.model.enume;

/**
 * 库存扣减状态
 *
 * @author Deep
 * @date 2022/5/2
 */
public enum DeductionEnums {
    /**
     * 无货存
     */
    NO_INVENTORY(0L, "无货存!"),
    /**
     * 货存不足
     */
    SHORT_INVENTORY(-1L, "货存不足!"),
    /**
     * 扣减成功 msg:保存订单号
     */
    DEDUCTION_SUCCESS(1L, "扣减成功!");
    /**
     * 状态码
     */
    private final Long status;

    /**
     * 描述信息
     */
    private final String msg;

    DeductionEnums(Long status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Long getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public static String getMsg(Long status) {
        if (status.equals(DEDUCTION_SUCCESS.status)) {
            return DEDUCTION_SUCCESS.msg;
        } else if (status.equals(SHORT_INVENTORY.status)) {
            return SHORT_INVENTORY.msg;
        } else {
            return NO_INVENTORY.getMsg();
        }
    }
}
