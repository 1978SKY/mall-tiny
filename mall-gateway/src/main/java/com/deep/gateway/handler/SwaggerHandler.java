package com.deep.gateway.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import springfox.documentation.swagger.web.*;

import java.util.Optional;

/**
 * 自定义Swagger的各个配置节点
 *
 * @author Deep
 * @date 2022/4/14
 */
@RestController
public class SwaggerHandler {

    private UiConfiguration uiConfiguration;
    private SecurityConfiguration securityConfiguration;
    private final SwaggerResourcesProvider swaggerResources;

    public SwaggerHandler(SecurityConfiguration securityConfiguration, UiConfiguration uiConfiguration, SwaggerResourcesProvider swaggerResources) {
        this.securityConfiguration = securityConfiguration;
        this.uiConfiguration = uiConfiguration;
        this.swaggerResources = swaggerResources;
    }

    @Autowired
    public SwaggerHandler(SwaggerResourcesProvider swaggerResources) {
        this.swaggerResources = swaggerResources;
    }

    /**
     * Swagger安全配置，支持oauth和apiKey设置
     */
    @GetMapping("/swagger-resources/configuration/security")
    public Mono<ResponseEntity<SecurityConfiguration>> securityConfiguration() {
        return Mono.just(new ResponseEntity<>(
                Optional.ofNullable(securityConfiguration).orElse(SecurityConfigurationBuilder.builder().build()), HttpStatus.OK));
    }

    /**
     * Swagger UI配置
     */
    @GetMapping("/swagger-resources/configuration/ui")
    public Mono<ResponseEntity<UiConfiguration>> uiConfiguration() {
        return Mono.just(new ResponseEntity<>(
                Optional.ofNullable(uiConfiguration).orElse(UiConfigurationBuilder.builder().build()), HttpStatus.OK));
    }

    /**
     * Swagger资源配置，微服务中这各个服务的api-docs信息
     */
    @GetMapping("/swagger-resources")
    public Mono<ResponseEntity> swaggerResources() {
        return Mono.just((new ResponseEntity<>(this.swaggerResources.get(), HttpStatus.OK)));
    }
}
