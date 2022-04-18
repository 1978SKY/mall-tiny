package com.deep.coupon.config;

import com.deep.coupon.interceptior.SwaggerInterceptor;
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
public class MallWebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SwaggerInterceptor()).addPathPatterns("/api/coupon/v2/api-docs");
    }
}
