package com.deep.order.model.enume;

/**
 * 订单生成时状态码
 * 
 * @author Deep
 * @date 2022/4/29
 */
public enum GenerateOrderEnum {

    /**
     * code状态码,msg 描述
     */

    FAILURE_TOKEN(1001, "order token失效!"),

    FAILURE_STOCK(1002, "订单中包含仓库不存在或库存不足的商品!"),

    FAILURE_UNKNOWN(1003, "系统未知异常!"),

    /**
     * 订单生成成功，msg为订单号
     */
    SUCCESS_CREATE(1004,"ordersn");

    private final Integer code;
    private String msg;

    GenerateOrderEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
