package com.deep.cart.feign;

import com.deep.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 商品模块远程调用服务
 *
 * @author Deep
 * @date 2022/4/4
 */
@FeignClient("mall-product")
public interface ProductFeignService {

    /**
     * 获取指定商品信息
     */
    @RequestMapping("/api/product/skuinfo/info/{skuId}")
    R info(@PathVariable("skuId") Long skuId);

    @GetMapping(value = "/api/product/skuinfo/saleAttr/{skuId}")
    R getSkuSaleAttrValues(@PathVariable("skuId") Long skuId);
}
