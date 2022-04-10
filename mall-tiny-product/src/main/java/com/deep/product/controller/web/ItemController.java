package com.deep.product.controller.web;

import com.deep.product.model.vo.SkuItemVO;
import com.deep.product.service.admin.SkuInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@Slf4j
@Api(value = "商品信息")
@Controller
@RequestMapping("/api/product/item")
public class ItemController {

    @Autowired
    private SkuInfoService skuInfoService;

    @GetMapping({"/{skuId}.html"})
    @ApiOperation("获取商品详情")
    public String skuItem(@PathVariable("skuId") Long skuId, Model model) {
        SkuItemVO result = skuInfoService.queryItem(skuId);
        model.addAttribute("item", result);
        return "newItem";
    }
}
