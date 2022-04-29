package com.deep.product.feign;

import com.deep.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * 库存服务远程调用
 *
 * @author Deep
 * @date 2022/3/29
 */
@FeignClient("mall-ware")
public interface WareFeignService {
    /**
     * 判断是否有商品库存
     */
    @PostMapping("/api/ware/waresku/hasStock")
    R isHasStock(List<Long> skuIds);
}
