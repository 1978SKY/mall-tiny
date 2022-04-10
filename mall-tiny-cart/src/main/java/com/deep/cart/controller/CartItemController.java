package com.deep.cart.controller;

import com.deep.cart.model.params.CheckParam;
import com.deep.cart.model.vo.CartItemVO;
import com.deep.cart.service.CartItemService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @ResponseBody
    @PostMapping(value = "checkItem")
    @ApiOperation("切换选中状态")
    public void updateSelectStatus(@RequestBody CheckParam checkParam) {
        // 更新选中状态
        cartItemService.updateCheckStatus(checkParam.getSkuId(), checkParam.getChecked());
    }

    @ResponseBody
    @GetMapping(value = "getCartItems")
    @ApiOperation("获取被选中的购物项")
    public List<CartItemVO> getCurrentCartItems() {
        return cartItemService.getCheckItems();
    }

}
