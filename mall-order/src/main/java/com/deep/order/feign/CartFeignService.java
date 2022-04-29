package com.deep.order.feign;

import com.deep.common.utils.R;
import com.deep.order.interceptor.FeignInterceptor;
import com.deep.order.model.dto.CartItemDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 购物车远程服务调用
 *
 * @author Deep
 * @date 2022/4/6
 */
@FeignClient(value = "mall-cart", configuration = FeignInterceptor.class)
public interface CartFeignService {
    /**
     * 获取当前用户购物车中的选项
     *
     * @param memberId 会员id
     */
    @GetMapping(value = "/api/cart/item/getCartItems")
    List<CartItemDTO> getCheckedItem();

    /**
     * 删除购物车商品
     * 
     * @param skuIds 商品id集合
     * @return 调用结果
     */
    @PostMapping("/api/cart/item/delItem")
    R deleteItem(@RequestBody List<Long> skuIds);
}
