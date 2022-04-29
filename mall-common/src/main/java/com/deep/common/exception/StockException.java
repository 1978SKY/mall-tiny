package com.deep.common.exception;

/**
 * 库存异常类
 * 
 * @author Deep
 * @date 2022/4/28
 */
public class StockException extends RuntimeException {
    public StockException(String message) {
        super(message);
    }

    public StockException(String message, Throwable cause) {
        super(message, cause);
    }
}
