package com.deep.search.exception;

/**
 * Elastic Search异常
 *
 * @author Deep
 * @date 2022/3/22
 */
public class EsException extends RuntimeException {
    public EsException(String message) {
        super(message);
    }

    public EsException(String message, Throwable cause) {
        super(message, cause);
    }
}
