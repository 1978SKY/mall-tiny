package com.deep.order.config;

import com.deep.order.interceptor.LoginInterceptor;
import com.deep.order.interceptor.SwaggerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 购物车web配置类
 *
 * @author Deep
 * @date 2022/4/3
 */
@Configuration
public class OrderWebConfig implements WebMvcConfigurer {



    /**
     * 注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/api/order/web/**", "/api/order/pay/**");
        registry.addInterceptor(new SwaggerInterceptor()).addPathPatterns("/api/order/v2/api-docs");
    }


}
