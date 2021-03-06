package com.deep.product.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * Swagger配置类
 *
 * @author Deep
 * @date 2022/3/12
 */
@EnableKnife4j
@Configuration
@EnableSwagger2WebMvc
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.deep.product.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Product")
                .description("商品模块API文档")
                .contact("Deep")
                .version("1.0")
                .build();
    }

//    @Bean
//    public Docket adminApi1() {
//        String[] basePackage = {"com.deep.product.controller.web"};
//        return Swagger2ConfigUtils.docket(
//                "web",
//                "Product-web",
//                "商品模块-前台",
//                basePackage);
//    }
//
//    @Bean
//    public Docket adminApi() {
//        String[] basePackage = {"com.deep.product.controller.admin"};
//        return Swagger2ConfigUtils.docket(
//                "admin",
//                "Product-admin",
//                "商品模块-后台",
//                basePackage);
//    }


}
