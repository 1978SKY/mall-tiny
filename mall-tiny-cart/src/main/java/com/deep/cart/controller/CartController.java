package com.deep.cart.controller;

import com.deep.cart.model.vo.CartItemVO;
import com.deep.cart.model.vo.CartVO;
import com.deep.cart.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.concurrent.ExecutionException;

/**
 * 购物车
 *
 * @author Deep
 * @date 2022/4/3
 */
@Api(value = "购物车")
@Controller
@RequestMapping("/api/cart/")
public class CartController {
    @Autowired
    private CartService cartService;


    @GetMapping(value = "/")
    @ApiOperation("购物车首页")
    public String cartIndex(Model model) {
        CartVO cartVO = cartService.getCartDetail();
        model.addAttribute("cart", cartVO);
        return "cartIndex";
    }

    @GetMapping(value = "/addToCart")
    @ApiOperation("添加到购物车")
    public String addToCart(@RequestParam("skuId") Long skuId,
                            @RequestParam("num") int count,
                            RedirectAttributes attributes) throws ExecutionException, InterruptedException {
        cartService.addToCart(skuId, count);
        // 给重定向请求用的【参数会拼接在下面请求之后】【转发会在请求域中】
        attributes.addAttribute("skuId", skuId);
        // 跳转添加成功页面防止表单重复提交
        return "redirect:http://localhost:88/api/cart/addToCartSuccess";
    }

    @GetMapping(value = "/addToCartSuccess")
    @ApiOperation("添加购物车成功")
    public String addToCartSuccessPage(@RequestParam("skuId") Long skuId, Model model) {
        // 重定向到成功页面
        CartItemVO cartItemVo = cartService.getCartItem(skuId);
        model.addAttribute("cartItem", cartItemVo);
        return "success";
    }
}
