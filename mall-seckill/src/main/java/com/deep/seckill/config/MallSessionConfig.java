package com.deep.seckill.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * spring-session 配置类
 *
 * @author Deep
 * @date 2022/4/16
 */
@Configuration
public class MallSessionConfig {
    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        // 设置session作用域
        cookieSerializer.setDomainName("mall.com");
        // 设置cookie name
        cookieSerializer.setCookieName("MALL_SESSION");
        cookieSerializer.setCookieMaxAge(60 * 60 * 24 * 7);
        return cookieSerializer;
    }

    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        return new GenericFastJsonRedisSerializer();
    }
}
