package com.deep.common.exception;

/**
 * BeanUtils 异常
 *
 * @author Deep
 * @date 2022/3/17
 */
public class BeanUtilsException extends RuntimeException {
    public BeanUtilsException(String message) {
        super(message);
    }

    public BeanUtilsException(String message, Throwable cause) {
        super(message, cause);
    }
}
