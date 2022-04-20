package com.deep.coupon.interceptior;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Swagger拦截器
 *
 * @author Deep
 * @date 2022/4/14
 */
@Component
public class SwaggerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();
        if (path.contains("/api/coupon/v2/api-docs")) {
            path = path.replaceAll("/api/coupon", "");
            request.getRequestDispatcher(path).forward(request, response);

            return false;
        }
        return true;
    }
}
