package com.deep.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web配置类
 *
 * @author Deep
 * @date 2022/3/30
 */
@Configuration
public class MallWebConfig implements WebMvcConfigurer {
    /**
     * 视图控制器（试图映射可以方便页面跳转（省略controller里面的方法））
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/api/auth/login").setViewName("login");
        registry.addViewController("/api/auth/register").setViewName("register");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");

        /** 配置knife4j 显示文档 */
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        /**
         * 配置swagger-ui显示文档
         */
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        /** 公共部分内容 */
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

//        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}
