package com.deep.product.controller.admin;

import com.deep.common.utils.R;
import com.deep.product.model.entity.CategoryEntity;
import com.deep.product.model.params.CategoryParam;
import com.deep.product.model.vo.CategoryVO;
import com.deep.product.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 商品分类
 *
 * @author Deep
 * @date 2022/3/15
 */
@Api(value = "商品分类")
@RestController
@RequestMapping("/api/product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list/tree")
    @ApiOperation("返回所有分类数据")
    public R list() {
        List<CategoryEntity> categoryTree
                = categoryService.getCategoryTree();

        return R.ok().put("data", categoryTree);
    }

    @PostMapping("/save")
    @ApiOperation("新增分类")
    public R save(@RequestBody CategoryParam category) {
        CategoryEntity entity = category.convertTo();
        categoryService.save(entity);

        return R.ok();
    }

    @PostMapping("/update")
    @ApiOperation("修改分类")
    public R update(@RequestBody CategoryParam category) {
        CategoryEntity entity = category.convertTo();
        categoryService.updateCategory(entity);

        return R.ok();
    }

    @PostMapping("/delete")
    @ApiOperation("删除分类")
    public R delete(@RequestBody Long[] catIds) {
        categoryService.logicDelete(Arrays.asList(catIds));

        return R.ok();
    }


}
