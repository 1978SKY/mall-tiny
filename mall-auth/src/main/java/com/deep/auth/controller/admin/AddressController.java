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
 * 收获地址
 *
 * @author Deep
 * @date 2022/4/5
 */
@Api(tags = "收获地址")
@Controller
@RequestMapping("/api/auth/member")
public class AddressController {

    @Autowired
    private MemberReceiveAddressService addressService;

    @ResponseBody
    @GetMapping(value = "/{memberId}/address")
    @ApiOperation("收货地址集合")
    public List<MemberReceiveAddressEntity> getAddress(@PathVariable("memberId") Long memberId) {

        return addressService.getAddresses(memberId);
    }

    @ResponseBody
    @GetMapping(value = "/address")
    @ApiOperation("收货地址")
    public MemberReceiveAddressEntity getMajorAddress(@RequestParam("addrId") Long addrId) {

        return addressService.getById(addrId);
    }
}
