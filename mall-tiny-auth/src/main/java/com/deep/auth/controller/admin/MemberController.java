package com.deep.auth.controller.admin;

import com.deep.auth.model.entity.MemberReceiveAddressEntity;
import com.deep.auth.service.MemberReceiveAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 会员控制器
 *
 * @author Deep
 * @date 2022/4/5
 */
@Api(tags = "会员控制器")
@Controller
@RequestMapping("/api/auth/member")
public class MemberController {

    @Autowired
    private MemberReceiveAddressService addressService;

    @ResponseBody
    @GetMapping(value = "/{memberId}/address")
    @ApiOperation("会员收货地址")
    public List<MemberReceiveAddressEntity> getAddress(@PathVariable("memberId") Long memberId) {

        return addressService.getAddress(memberId);
    }

    @ResponseBody
    @GetMapping(value = "/address")
    @ApiOperation("会员收货地址")
    public MemberReceiveAddressEntity getMajorAddress(@RequestParam("addrId") Long addrId) {

        return addressService.getById(addrId);
    }
}
