package com.deep.ware.config;

import com.deep.ware.config.properties.ThreadPoolConfigProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程配置类（异步编程）
 *
 * @author Deep
 * @date 2022/2/4
 */
@Configuration
@EnableConfigurationProperties(ThreadPoolConfigProperties.class)
public class ThreadPoolConfig {
    /**
     * 自定义线程池
     *
     * @return 线程池
     */
    @Bean
    public ThreadPoolExecutor threadPool(ThreadPoolConfigProperties pool) {
        return new ThreadPoolExecutor(
                pool.getCoreSize(),
                pool.getMaxSize(),
                pool.getKeepAliveTime(),
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(100000),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
    }
}
