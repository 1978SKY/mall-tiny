package com.deep.seckill.config;

import com.deep.seckill.interceptor.SwaggerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * web配置类
 *
 * @author Deep
 * @date 2022/3/30
 */
@Configuration
public class SeckillWebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SwaggerInterceptor()).addPathPatterns("/api/seckill/v2/api-docs");
    }
}
