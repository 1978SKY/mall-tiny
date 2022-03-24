package com.deep.product.controller.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 商品信息
 *
 * @author Deep
 * @date 2022/3/24
 */
//@Api(value = "商品信息")
//@Controller
//@RequestMapping("/api/product/item")
//public class ItemController {
//
//    @GetMapping({"/{skuId}.html"})
//    @ApiOperation("获取商品详情")
//    public String skuItem(@PathVariable("skuId") Long skuId, Model model) {
//        SkuItemVO result = skuInfoService.queryItem(skuId);
//        model.addAttribute("item", result);
//        return "item";
//    }
//}
