package com.deep.common.exception;

/**
 * 远程调用异常
 *
 * @author Deep
 * @date 2022/3/29
 */
public class FeignRequestException extends RuntimeException {
    public FeignRequestException(String message) {
        super(message);
    }

    public FeignRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
