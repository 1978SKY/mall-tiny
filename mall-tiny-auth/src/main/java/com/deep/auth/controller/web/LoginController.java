package com.deep.auth.controller.web;

import com.deep.auth.model.constant.AuthConstant;
import com.deep.auth.model.entity.MemberEntity;
import com.deep.auth.model.params.LoginParam;
import com.deep.auth.model.params.RegParam;
import com.deep.auth.service.LoginService;
import com.deep.common.model.dto.MemberDTO;
import com.deep.common.utils.BeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 登录
 *
 * @author Deep
 * @date 2022/2/4
 */
@Slf4j
@Api("普通登录")
@Controller
@RequestMapping("/api/auth/login")
public class LoginController {
    @Autowired
    private LoginService loginService;


    @ResponseBody
    @PostMapping("/doLogin")
    @ApiOperation("登录操作")
    public String doLogin(@RequestBody LoginParam loginParam, HttpSession session) {
        MemberEntity member = loginService.login(loginParam);
        if (member != null) {
            MemberDTO memberDTO = BeanUtils.transformFrom(member, MemberDTO.class);
            session.setAttribute(AuthConstant.LOGIN_USER, memberDTO);
            return "登录成功!";
        }
        return "用户名密码错误!";
    }

    @GetMapping("/logout")
    @ApiOperation("注销操作")
    public String logout(HttpSession session) {

        session.removeAttribute(AuthConstant.LOGIN_USER);
        // 重定向回登录页
        return "redirect:http://localhost:8000/api/auth/login";
    }

    @ResponseBody
    @PostMapping("/doRegister")
    @ApiOperation("注册操作")
    public String doRegister(@RequestBody RegParam regParam) {
        System.out.println(regParam);
        return "Do register success";
    }

}
