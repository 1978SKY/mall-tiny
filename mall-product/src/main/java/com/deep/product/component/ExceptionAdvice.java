package com.deep.product.component;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ValidationException;

import org.apache.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.deep.common.utils.R;

import lombok.extern.slf4j.Slf4j;

/**
 * 异常统一处理
 * 
 * @author Deep
 * @date 2022/4/26
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.deep.product.controller")
public class ExceptionAdvice {

    /**
     * 校验方法参数时异常
     * 
     * @return 异常处理结果
     */
    @ExceptionHandler(value = ValidationException.class)
    public R handleValidException(MethodArgumentNotValidException exception) {
        Map<String, String> errorMap = new HashMap<>();
        BindingResult bindingResult = exception.getBindingResult();
        // 处理错误
        bindingResult.getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        log.error("校验数据错误{}", errorMap);
        return R.error(HttpStatus.SC_FORBIDDEN, "传入数据非法!").put("data", errorMap);
    }

    /**
     * 服务器内部处理错误
     *
     * @return 异常处理结果
     */
    @ExceptionHandler(value = Exception.class)
    public R handleException(Exception exception) {
        log.error("{}处理逻辑异常：{}", exception.getClass(), exception.getMessage());
        return R.error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "服务器内部异常，请联系管理员!");
    }

}
