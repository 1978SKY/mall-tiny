package com.deep.order.excption;

/**
 * 订单异常类
 *
 * @author Deep
 * @date 2022/4/30
 */
public class OrderException extends RuntimeException {
    public OrderException(String message) {
        super(message);
    }

    public OrderException(String message, Throwable cause) {
        super(message, cause);
    }
}
