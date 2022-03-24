package com.deep.product.controller.web;

import com.deep.product.model.entity.CategoryEntity;
import com.deep.product.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商城首页
 *
 * @author Deep
 * @date 2022/3/13
 */
@Api(value = "商城首页")
@Controller
//@RequestMapping("/api/product")
public class IndexController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 获取所有商品分类
     */
    @ResponseBody
    @GetMapping("/index/catalog")
    public List<CategoryEntity> getCatalogJson() {
        return categoryService.getCategoryTree();
    }

    @GetMapping({"/", "index.html"})
    @ApiOperation("获取首页")
    public String indexPage(Model model) {
        List<CategoryEntity> entities = categoryService.getLevel(1);
        model.addAttribute("categorys", entities);
        model.addAttribute("items", null);
        return "index";
    }
}
