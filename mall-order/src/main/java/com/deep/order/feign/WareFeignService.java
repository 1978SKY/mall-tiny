package com.deep.order.feign;

import com.deep.common.utils.R;
import com.deep.order.interceptor.FeignInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 库存远程调用
 *
 * @author Deep
 * @date 2022/4/6
 */
@FeignClient(value = "mall-tiny-ware")
public interface WareFeignService {
    /**
     * 判断是否有商品库存
     */
    @PostMapping("/api/ware/waresku/hasStock")
    R isHasStock(@RequestBody List<Long> skuIds);
}
