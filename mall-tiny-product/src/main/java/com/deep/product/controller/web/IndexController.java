package com.deep.product.controller.web;

import com.deep.product.model.entity.CategoryEntity;
import com.deep.product.model.entity.SkuInfoEntity;
import com.deep.product.service.admin.CategoryService;
import com.deep.product.service.web.IndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 商城首页
 *
 * @author Deep
 * @date 2022/3/13
 */
@Api(tags = "商城首页")
@Controller
@RequestMapping("/api/product")
public class IndexController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private IndexService indexService;

    /**
     * 获取所有商品分类
     */
    @ResponseBody
    @GetMapping("/index/catalog")
    public List<CategoryEntity> getCatalogJson() {
        return categoryService.getCategoryTree();
    }

    @GetMapping({"/", "index"})
    @ApiOperation("获取首页")
    public String indexPage(Model model) {
        List<SkuInfoEntity> products = indexService.getProducts();
        model.addAttribute("products1", products.subList(0, 4));
        model.addAttribute("products2", products.subList(4, 8));
        model.addAttribute("products3", products.subList(8, 12));


        return "newIndex";
    }
}
