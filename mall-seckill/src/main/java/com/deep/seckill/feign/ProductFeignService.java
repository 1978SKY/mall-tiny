package com.deep.seckill.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.deep.common.utils.R;

/**
 * 商品模块远程调用
 * 
 * @author Deep
 * @date 2022/4/21
 */
@FeignClient(value = "mall-tiny-product")
public interface ProductFeignService {
    /**
     * 批量获取商品
     * 
     * @param skuIds 商品id集合
     * @return 商品
     */
    @PostMapping("/api/product/skuinfo/info/skuIds")
    R infos(@RequestBody List<Long> skuIds);
}
