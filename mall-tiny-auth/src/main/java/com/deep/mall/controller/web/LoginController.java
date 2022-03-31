package com.deep.mall.controller.web;

import com.deep.mall.model.params.LoginParam;
import com.deep.mall.model.params.RegParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 登录
 *
 * @author Deep
 * @date 2022/2/4
 */
@Slf4j
@Controller
@RequestMapping("/api/auth/login")
public class LoginController {


    @ResponseBody
    @PostMapping("/doLogin")
    @ApiOperation("登录操作")
    public String doLogin(@RequestBody LoginParam loginParam) {
        System.out.println(loginParam);
        return "Do login success";
    }

    @ResponseBody
    @PostMapping("/doRegister")
    @ApiOperation("登录操作")
    public String doRegister(@RequestBody RegParam regParam) {
        System.out.println(regParam);
        return "Do register success";
    }


}
