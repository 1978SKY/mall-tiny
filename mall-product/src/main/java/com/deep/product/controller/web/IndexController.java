package com.deep.product.controller.web;

import static com.deep.product.model.constant.IndexConstant.*;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deep.product.model.entity.CategoryEntity;
import com.deep.product.model.vo.ProductVo;
import com.deep.product.service.CategoryService;
import com.deep.product.service.IndexService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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

    private final CategoryService categoryService;
    private final IndexService indexService;

    public IndexController(CategoryService categoryService, IndexService indexService) {
        this.categoryService = categoryService;
        this.indexService = indexService;
    }

    @GetMapping({"index.html"})
    @ApiOperation("商城首页")
    public String indexPageFinal(Model model) {
        List<ProductVo> trendingProducts = indexService.getTrendingProduct(TRENDING_PRODUCT_COUNT);
        List<List<ProductVo>> selectProducts =
            indexService.getSelectProduct(SELECTED_PRODUCT_PAGE, SELECTED_PRODUCT_PRE_PAGE);
        List<ProductVo> bestSaleProducts = indexService.getBestSaleEveryWeek(BEST_SEAL_EVERY_WEEK);

        model.addAttribute("trendingProducts", trendingProducts);
        model.addAttribute("selectProducts", selectProducts);
        model.addAttribute("bestSaleProducts", bestSaleProducts);

        return "index";
    }

    @ResponseBody
    @GetMapping("/index/catalog")
    @ApiOperation("获取所有商品分类")
    public List<CategoryEntity> getCatalogJson() {
        return categoryService.getCategoryTree();
    }
}
