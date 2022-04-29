package com.deep.auth.service;

import com.deep.auth.model.entity.MemberReceiveAddressEntity;
import com.deep.common.model.dto.OrderDto;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * 账户管理service
 * 
 * @author Deep
 * @date 2022/4/25
 */
public interface AccountService {
    /**
     * 获取会员订单
     * 
     * @param memberId 会员id
     * @return 订单集合
     */
    List<OrderDto> getOrders(@NonNull Long memberId);
}
