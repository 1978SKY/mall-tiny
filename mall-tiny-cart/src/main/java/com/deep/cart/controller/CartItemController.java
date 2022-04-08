package com.deep.cart.controller;

import com.deep.cart.model.vo.CartItemVO;
import com.deep.cart.service.CartItemService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 购物项
 *
 * @author Deep
 * @date 2022/4/5
 */
@Controller
@RequestMapping("/api/cart/item")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @GetMapping(value = "checkItem")
    @ApiOperation("切换选中状态")
    public String updateSelectStatus(@RequestParam("skuId") Long skuId,
                                     @RequestParam(value = "checked", defaultValue = "1") Integer checked) {
        // 更新选中状态
        cartItemService.updateCheckStatus(skuId, checked);
        return "redirect:http://localhost:88/api/cart/";
    }

    @ResponseBody
    @GetMapping(value = "getCartItems")
    @ApiOperation("获取被选中的购物项")
    public List<CartItemVO> getCurrentCartItems() {
        return cartItemService.getCheckItems();
    }

}
