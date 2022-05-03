package com.deep.product.model.enume;

/**
 * 商品属性规格
 *
 * @author Deep
 * @date 2022/3/18
 */
public enum AttrEnum {
    /**
     * 基本属性
     */
    ATTR_TYPE_BASE(1, "基本属性"),
    /**
     * 销售属性
     */
    ATTR_TYPE_SALE(0, "销售属性");

    private int code;

    private String msg;

    AttrEnum(int code, String msg) {
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
