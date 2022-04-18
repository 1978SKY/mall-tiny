package com.deep.seckill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 秒杀模块启动类
 *
 * @author Deep
 * @date 2022/4/17
 */
@EnableFeignClients("com.deep.seckill.feign")
@SpringBootApplication
public class SeckillApplication {
    public static void main(String[] args) {
        SpringApplication.run(SeckillApplication.class, args);
    }
}
