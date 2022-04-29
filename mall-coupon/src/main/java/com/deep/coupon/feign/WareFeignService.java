package com.deep.coupon.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.deep.common.utils.R;

/**
 * 库存模块远程服务
 * 
 * @author Deep
 * @date 2022/4/19
 */
@FeignClient("mall-ware")
public interface WareFeignService {

    /**
     * 检查库存并锁定
     * 
     * @param skuId 商品id
     * @param count 商品数量
     * @return 结果
     */
    @GetMapping("/api/ware/waresku/lockInventory")
    R checkAndLockStock(@RequestParam("skuId") Long skuId, @RequestParam("count") Integer count);
}
