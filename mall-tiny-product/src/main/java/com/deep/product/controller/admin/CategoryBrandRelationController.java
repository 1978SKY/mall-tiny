package com.deep.product.controller.admin;

import com.deep.common.utils.R;
import com.deep.product.model.params.CategoryBrandRelationParam;
import com.deep.product.service.CategoryBrandRelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 分类品牌关联关系
 *
 * @author Deep
 * @date 2022/3/17
 */
@Api(value = "分类品牌关联关系")
@RestController
@RequestMapping("/api/product/categorybrandrelation")
public class CategoryBrandRelationController {
    @Autowired
    private CategoryBrandRelationService relationService;

    @PostMapping("/save")
    @ApiOperation("新增管理关系")
    public R save(@RequestBody CategoryBrandRelationParam relationParam) {
        relationService.save(relationParam.getCatId(), relationParam.getBrandId());

        return R.ok();
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除关联关系")
    public R delete(@RequestBody Long[] ids) {
        relationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
}
