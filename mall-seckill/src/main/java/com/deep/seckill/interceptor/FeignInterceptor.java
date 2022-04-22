package com.deep.seckill.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * Feign拦截器（解决Feign请求头丢失问题）
 *
 * @author Deep
 * @date 2022/4/6
 */
//@Component
public class FeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return;
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                String values = request.getHeader(name);
                // 跳过 content-length，解决too many bites written的问题
                if ("content-length".equalsIgnoreCase(name)) {
                    continue;
                }
                template.header(name, values);
            }
        }
    }
}
