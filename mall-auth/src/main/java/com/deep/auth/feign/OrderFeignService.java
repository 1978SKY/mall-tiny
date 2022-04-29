package com.deep.auth.feign;

import com.deep.common.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 订单服务远程调用
 * 
 * @author Deep
 * @date 2022/4/25
 */
@FeignClient("mall-order")
public interface OrderFeignService {
    /**
     * 获取用户订单
     * @param memberId 会员id
     * @return 调用结果
     */
    @GetMapping("/api/order/order/{memberId}/orders")
    R getMemberOrders(@PathVariable("memberId") Long memberId);
}
