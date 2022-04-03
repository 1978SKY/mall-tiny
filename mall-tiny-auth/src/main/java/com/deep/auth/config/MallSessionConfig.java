package com.deep.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * spring-session 配置类
 *
 * @author Deep
 * @date 2022/4/1
 */
@Configuration
public class MallSessionConfig {
    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        // 设置session作用域
        cookieSerializer.setDomainName("localhost");
        // 设置cookie name
        cookieSerializer.setCookieName("MALL_SESSION");
        cookieSerializer.setCookieMaxAge(60 * 60 * 24 * 7);
        return cookieSerializer;
    }

    /**
     * 序列化方式（Json）
     */
    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }
}
