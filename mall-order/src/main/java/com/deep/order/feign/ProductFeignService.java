package com.deep.order.feign;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * 商品远程调用服务
 *
 * @author Deep
 * @date 2022/4/7
 */
@FeignClient("mall-tiny-product")
public interface ProductFeignService {

}
