package com.deep.ware.model.constant;

/**
 * 库存常量
 *
 * @author Deep
 * @date 2022/3/28
 */
public class WareConstant {
    /**
     * 采购单状态
     */
    public enum PurchaseStatusEnum {
        CREATED(0, "新建"), ASSIGNED(1, "已分配"),
        RECEIVE(2, "已领取"), FINISH(3, "已完成"),
        HASERROR(4, "有异常");
        private int code;
        private String msg;

        PurchaseStatusEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }

    /**
     * 采购需求状态
     */
    public enum PurchaseDemandStatusEnum {
        CREATED(0, "新建"), ASSIGNED(1, "已分配"),
        BUYING(2, "正在采购"), FINISH(3, "已完成"),
        HASERROR(4, "采购失败");
        private int code;
        private String msg;

        PurchaseDemandStatusEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }
}
