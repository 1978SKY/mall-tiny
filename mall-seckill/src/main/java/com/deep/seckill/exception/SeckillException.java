package com.deep.seckill.exception;

/**
 * 秒杀异常类
 *
 * @author Deep
 * @date 2022/5/2
 */
public class SeckillException extends RuntimeException {
    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
