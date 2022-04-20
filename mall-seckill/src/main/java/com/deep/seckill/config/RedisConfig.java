package com.deep.seckill.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author Deep
 * @date 2022/4/19
 */
@Configuration
public class RedisConfig {

    /**
     * Redisson
     * 
     * @return redisson client
     */
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://124.222.56.141:6379").setPassword("Yi1Ge2Ren3Redis.");
        return Redisson.create(config);
    }

    /**
     * 序列化方式（Json）
     */
    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        return new GenericJackson2JsonRedisSerializer(new ObjectMapper());
    }
}
