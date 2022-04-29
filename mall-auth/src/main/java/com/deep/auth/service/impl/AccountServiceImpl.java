package com.deep.auth.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.deep.auth.feign.OrderFeignService;
import com.deep.auth.service.AccountService;
import com.deep.common.exception.FeignRequestException;
import com.deep.common.model.dto.OrderDto;
import com.deep.common.utils.R;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 账户管理
 * 
 * @author Deep
 * @date 2022/4/25
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {
    private final OrderFeignService orderFeignService;

    public AccountServiceImpl(OrderFeignService orderFeignService) {
        this.orderFeignService = orderFeignService;
    }

    @Override
    public List<OrderDto> getOrders(@NonNull Long memberId) {
        Assert.notNull(memberId, "会员id不能为空!");
        R r = orderFeignService.getMemberOrders(memberId);
        if (r.getCode() != 0) {
            throw new FeignRequestException("订单服务远程错误!");
        }
        return r.getData("data", new TypeReference<>() {});
    }
}
