package com.deep.ware.model;

/**
 * 库存枚举类
 *
 * @author Deep
 * @date 2022/4/29
 */
public enum StockEnum {
    // 1-已锁定 2-已解锁 3-扣减
    LOCKED(1, "已锁定"),
    UNLOCKED(2, "已解锁"),
    DEDUCTION(3, "扣减");

    private final Integer status;
    private final String msg;

    StockEnum(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
