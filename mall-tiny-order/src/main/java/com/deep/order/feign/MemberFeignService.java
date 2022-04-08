package com.deep.order.feign;

import com.deep.order.model.dto.MemberAddressDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 会员远程服务
 *
 * @author Deep
 * @date 2022/4/5
 */
@FeignClient(value = "mall-tiny-auth")
public interface MemberFeignService {
    /**
     * 获取会员地址集合
     *
     * @param memberId 会员id
     * @return 会员地址集合
     */
    @GetMapping(value = "/api/auth/member/{memberId}/address")
    List<MemberAddressDTO> getMemberAddress(@PathVariable("memberId") Long memberId);

    /**
     * 收货地址
     *
     * @param addrId 收货地址id
     * @return 收获地址
     */
    @GetMapping(value = "/api/auth/member/address")
    MemberAddressDTO getMajorAddress(@RequestParam("addrId") Long addrId);
}
