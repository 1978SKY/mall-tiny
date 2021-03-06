package com.deep.product.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.deep.product.model.vo.SkuItemVo;
import com.deep.product.service.SkuInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品信息
 *
 * @author Deep
 * @date 2022/3/24
 */
@Api(tags = "商品信息")
@Controller
@RequestMapping("/api/product/item")
public class ItemController {

    @Autowired
    private SkuInfoService skuInfoService;

    @GetMapping({"/{skuId}.html"})
    @ApiOperation("获取商品详情")
    public String skuItem(@PathVariable("skuId") Long skuId, Model model) {
        SkuItemVo result = skuInfoService.queryItem(skuId);
        model.addAttribute("product", result);
        return "item";
    }

    // @GetMapping({"/{skuId}.html"})
    // @ApiOperation("获取商品详情")
    // public String skuItem(@PathVariable("skuId") Long skuId, Model model) {
    // SkuItemVO result = skuInfoService.queryItem(skuId);
    // model.addAttribute("item", result);
    // return "newItem";
    // }
}
