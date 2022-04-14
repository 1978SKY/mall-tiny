package com.deep.common.utils;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author Deep
 * @date 2022/4/13
 */
/*public class Swagger2ConfigUtils {
    *//**
     * 配置模块
     *
     * @param moduleCode  模块Code
     * @param moduleName  模块名称
     * @param basePackage 基础包(多个)
     * @return
     *//*
    public static Docket docket(String moduleCode, String moduleName, String description, String... basePackage) {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo(moduleName, description))
                .groupName(moduleCode).select()
                .apis(Predicates.or(RequestHandlerSelectors.withMethodAnnotation(ResponseBody.class),
                        basePackage(basePackage)))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
//                .paths(PathSelectors.any())
                .build();

    }

    *//**
     * 声明基础包
     *
     * @param basePackage 基础包路径
     *//*
    public static Predicate<RequestHandler> basePackage(final String... basePackage) {
        return input -> declaringClass(input).map(handlerPackage(basePackage)).orElse(true);
    }

    *//**
     * 校验基础包
     *
     * @param basePackage 基础包路径
     *//*
    private static Function<Class<?>, Boolean> handlerPackage(final String... basePackage) {
        return input -> {
            for (String strPackage : basePackage) {
                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }

    *//**
     * 检验基础包实例
     *
     * @param requestHandler 请求处理类
     *//*
    @SuppressWarnings("deprecation")
    private static Optional<Class<?>> declaringClass(RequestHandler requestHandler) {
        return Optional.ofNullable(requestHandler.declaringClass());
    }

    *//**
     * 配置在线文档的基本信息
     *//*
    private static ApiInfo apiInfo(String title, String description) {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .version("1.0")
                .build();
    }
}*/
