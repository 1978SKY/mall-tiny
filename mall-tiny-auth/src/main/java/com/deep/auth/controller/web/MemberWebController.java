package com.deep.auth.controller.web;

import com.alibaba.fastjson.JSON;
import com.deep.auth.model.entity.MemberReceiveAddressEntity;
import com.deep.auth.model.params.PasswordParam;
import com.deep.auth.service.MemberReceiveAddressService;
import com.deep.auth.service.MemberService;
import com.deep.common.model.constant.AuthConstant;
import com.deep.common.model.dto.MemberDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 会员控制器web端
 *
 * @author Deep
 * @date 2022/4/12
 */
@Api(tags = "会员控制器web端")
@Controller
@RequestMapping("/api/auth/user")
public class MemberWebController {

    @Autowired
    private MemberReceiveAddressService addressService;
    @Autowired
    private MemberService memberService;

    @GetMapping("/center")
    @ApiOperation("个人中心")
    public String center(HttpSession session) {
        if (session.getAttribute(AuthConstant.LOGIN_USER) == null) {
            return "redirect:http://localhost:8000/api/auth/login";
        }
        return "mygrxx";
    }

    @GetMapping("/address")
    @ApiOperation("地址管理")
    public String address(HttpSession session, Model model) {
        if (session.getAttribute(AuthConstant.LOGIN_USER) == null) {
            return "redirect:http://localhost:8000/api/auth/login";
        }
        String json = JSON.toJSONString(session.getAttribute(AuthConstant.LOGIN_USER));
        MemberDTO member = JSON.parseObject(json, MemberDTO.class);
        List<MemberReceiveAddressEntity> addresses = addressService.getAddress(member.getId());
        model.addAttribute("addresses", addresses);
        return "address";
    }

    @GetMapping("/repassword")
    @ApiOperation("修改密码页面")
    public String password(HttpSession session) {
        if (session.getAttribute(AuthConstant.LOGIN_USER) == null) {
            return "redirect:http://localhost:8000/api/auth/login";
        }
        return "password";
    }

    @ResponseBody
    @PostMapping("/doRepassword")
    @ApiOperation("修改密码")
    public String doRepassword(@RequestBody PasswordParam param, HttpSession session) {
        if (session.getAttribute(AuthConstant.LOGIN_USER) == null) {
            return "redirect:http://localhost:8000/api/auth/login";
        }
        return memberService.updatePassword(param) ? "修改成功!" : "修改失败!";
    }

}
