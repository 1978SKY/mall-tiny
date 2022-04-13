package com.deep.order.model.enume;

/**
 * 订单状态枚举类
 *
 * @author Deep
 * @date 2022/4/6
 */
public enum OrderStatusEnum {
    // 新建态
    CREATE_NEW(0, "待付款"),
    PAYED(1, "已付款"),
    SENDED(2, "已发货"),
    RECIEVED(3, "已完成"),
    CANCLED(4, "已取消"),
    SERVICING(5, "售后中"),
    SERVICED(6, "售后完成");

    private final Integer code;
    private final String msg;

    OrderStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static String checkStatus(int status) {
        switch (status) {
            case 0:
                return CREATE_NEW.msg;
            case 1:
                return PAYED.msg;
            case 2:
                return SENDED.msg;
            case 3:
                return RECIEVED.msg;
            case 4:
                return CANCLED.msg;
            case 5:
                return SERVICING.msg;
            case 6:
                return SERVICED.msg;
            default:
                break;
        }
        return "";
    }
}
