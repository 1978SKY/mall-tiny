package com.deep.common.exception;

/**
 * 登录异常
 *
 * @author Deep
 * @date 2022/4/5
 */
public class BadLoginException extends RuntimeException {
    public BadLoginException(String message) {
        super(message);
    }

    public BadLoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
