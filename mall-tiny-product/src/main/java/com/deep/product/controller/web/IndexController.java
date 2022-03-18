package com.deep.product.controller.web;

import com.deep.product.model.entity.CategoryEntity;
import com.deep.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Deep
 * @date 2022/3/13
 */
@Controller
@RequestMapping("/api/product")
public class IndexController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 获取所有商品分类
     */
    @ResponseBody
    @GetMapping("index/catalog")
    public List<CategoryEntity> getCatalogJson() {
        return categoryService.getCategoryTree();
    }
}
