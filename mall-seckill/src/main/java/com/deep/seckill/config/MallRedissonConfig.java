package com.deep.seckill.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 分布式锁
 *
 * @author Deep
 * @date 2022/4/18
 */
@Configuration
public class MallRedissonConfig {

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://124.222.56.141:6379").setPassword("Yi1Ge2Ren3Redis.");
        return Redisson.create(config);
    }
}
