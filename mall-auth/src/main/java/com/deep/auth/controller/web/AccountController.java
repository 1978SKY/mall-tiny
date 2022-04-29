package com.deep.auth.controller.web;

import com.alibaba.fastjson.JSON;
import com.deep.auth.model.entity.MemberEntity;
import com.deep.auth.model.entity.MemberReceiveAddressEntity;
import com.deep.auth.model.params.PasswordParam;
import com.deep.auth.service.AccountService;
import com.deep.auth.service.MemberReceiveAddressService;
import com.deep.auth.service.MemberService;
import com.deep.common.model.constant.AuthConstant;
import com.deep.common.model.dto.MemberDTO;
import com.deep.common.model.dto.OrderDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 会员控制器web端
 *
 * @author Deep
 * @date 2022/4/12
 */
@Api(tags = "会员")
@Controller
@RequestMapping("/api/auth/account")
public class AccountController {

    @Autowired
    private MemberReceiveAddressService addressService;
    @Autowired
    private MemberService memberService;

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/account.html")
    @ApiOperation("账户管理")
    public String myAccount(HttpSession session, Model model) {
        String memberStr = JSON.toJSONString(session.getAttribute(AuthConstant.LOGIN_USER));
        if ("null".equalsIgnoreCase(memberStr) || !StringUtils.hasLength(memberStr)) {
            return "redirect:http://localhost:88/api/auth/login.html";
        }
        Long memberId = JSON.parseObject(memberStr, MemberDTO.class).getId();
        List<OrderDto> allOrders = accountService.getOrders(memberId);
        List<OrderDto> orders = allOrders.stream().filter(item -> item.getStatus() < 3).collect(Collectors.toList());
        allOrders.removeAll(orders);
        model.addAttribute("orders", orders);
        model.addAttribute("reviewOrders", allOrders);
        List<MemberReceiveAddressEntity> addresses = addressService.getAddresses(memberId);
        model.addAttribute("addresses", addresses);

        MemberEntity member = memberService.getById(memberId);
        model.addAttribute("member", member);

        return "account";
    }

    @GetMapping("/center")
    @ApiOperation(value = "个人中心")
    public String center() {
        return "myDetail";
    }

    @GetMapping("/address")
    @ApiOperation(value = "地址管理")
    public String address(HttpSession session, Model model) {
        if (session.getAttribute(AuthConstant.LOGIN_USER) == null) {
            return "redirect:http://localhost:8000/api/auth/login";
        }
        String json = JSON.toJSONString(session.getAttribute(AuthConstant.LOGIN_USER));
        MemberDTO member = JSON.parseObject(json, MemberDTO.class);
        List<MemberReceiveAddressEntity> addresses = addressService.getAddresses(member.getId());
        model.addAttribute("addresses", addresses);
        return "address";
    }

    @GetMapping("/repassword")
    @ApiOperation(value = "修改密码页面")
    public String password() {

        return "password";
    }

    @ResponseBody
    @PostMapping("/doRepassword")
    @ApiOperation("修改密码")
    public String doRepassword(@RequestBody PasswordParam param) {
        return memberService.updatePassword(param) ? "修改成功!" : "修改失败!";
    }

}
