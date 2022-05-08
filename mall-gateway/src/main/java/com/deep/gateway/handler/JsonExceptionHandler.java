package com.deep.gateway.handler;

import com.alibaba.fastjson.JSON;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.ApplicationContext;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Json异常处理器
 * 
 * @author Deep
 * @date 2022/4/28
 */
@Slf4j
public class JsonExceptionHandler extends DefaultErrorWebExceptionHandler {

    public JsonExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources,
        ErrorProperties errorProperties, ApplicationContext applicationContext) {
        super(errorAttributes, resources, errorProperties, applicationContext);
    }

    /**
     * 获取异常属性
     * 
     * @param request request
     * @param options options
     * @return map
     */
    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        int code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        Throwable error = super.getError(request);
        String message = buildMessage(request, error);
        if (error instanceof NotFoundException) {
            code = HttpStatus.NOT_FOUND.value();
            message = "找不到url!";
        }
        return response(code, message);
    }

    /**
     * 错误响应格式(HTML/JSON,此处为JSON)
     * 
     * @param errorAttributes 错误属性
     * @return RouterFunction
     */
    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    /**
     * 响应状态码
     * 
     * @param errorAttributes 错误属性
     * @return 状态码
     */
    @Override
    protected int getHttpStatus(Map<String, Object> errorAttributes) {
        return (int)errorAttributes.get("status");
    }

    /**
     * 构建异常信息
     *
     * @param request request
     * @param throwable 异常超类
     * @return 异常信息
     */
    private String buildMessage(ServerRequest request, Throwable throwable) {
        StringBuilder message = new StringBuilder("Failed to handle request [");
        message.append(request.methodName());
        message.append(" ");
        message.append(request.uri());
        message.append("]");
        if (throwable != null) {
            message.append(": ");
            message.append(throwable.getMessage());
        }
        return message.toString();
    }

    /**
     * 构建响应数据
     * 
     * @param status 响应状态码
     * @param errorMessage 错误信息
     * @return map集合
     */
    private static Map<String, Object> response(int code, String errorMessage) {
        Map<String, Object> map = new HashMap<>(4);
        map.put("code", code);
        map.put("status", HttpStatus.OK.value());
        map.put("message", errorMessage);
        log.error("异常返回：{}", map);
        return map;
    }
}
