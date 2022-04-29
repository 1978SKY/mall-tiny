package com.deep.cart.interceptor;

import com.alibaba.fastjson.JSON;
import com.deep.common.model.constant.AuthConstant;
import com.deep.common.model.dto.MemberDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录拦截器
 *
 * @author Deep
 * @date 2022/4/3
 */
public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 将登录的用户存入本地线程进行共享
     */
    public static final ThreadLocal<MemberDTO> LOGIN_USER_THREAD_LOCAL = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String json = JSON.toJSONString(session.getAttribute(AuthConstant.LOGIN_USER));
        MemberDTO member = JSON.parseObject(json, MemberDTO.class);
        if (member != null) {
            LOGIN_USER_THREAD_LOCAL.set(member);
            return true;
        }
        // 未登录
        response.sendRedirect("http://localhost:88/api/auth/login.html");
        return false;
    }
}
