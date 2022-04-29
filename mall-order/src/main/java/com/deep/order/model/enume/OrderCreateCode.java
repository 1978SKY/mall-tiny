package com.deep.order.model.enume;

/**
 * 订单生成时状态码
 * 
 * @author Deep
 * @date 2022/4/29
 */
public enum OrderCreateCode {

    /**
     * code状态码,msg 描述
     */

    TOKEN_FAILURE(1001, "order token失效!"),

    STOCK_FAILURE(1002, "订单中包含仓库不存在或库存不足的商品!"),

    UNKNOWN_FAILURE(1003, "系统未知异常!"),

    CREATE_SUCCESS(1004,"订单生成成功");

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    private Integer code;
    private String msg;

    OrderCreateCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String checkStatus(int code) {
        switch (code) {
            case 0:
                return TOKEN_FAILURE.msg;
            case 1:
                return TOKEN_FAILURE.msg;
            case 2:
                return TOKEN_FAILURE.msg;
            case 3:
                return TOKEN_FAILURE.msg;
            case 4:
                return TOKEN_FAILURE.msg;
            case 5:
                return TOKEN_FAILURE.msg;
            case 6:
                return TOKEN_FAILURE.msg;
            default:
                break;
        }
        return "";
    }

}
