package com.deep.product.controller.admin;

import com.deep.common.utils.BeanUtils;
import com.deep.common.utils.R;
import com.deep.product.model.entity.CategoryBrandRelationEntity;
import com.deep.product.model.params.CategoryBrandRelationParam;
import com.deep.product.model.vo.BrandVO;
import com.deep.product.service.CategoryBrandRelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 分类品牌关联关系
 *
 * @author Deep
 * @date 2022/3/17
 */
@Api(tags = "分类品牌关联关系")
@RestController
@RequestMapping("/api/product/categorybrandrelation")
public class CategoryBrandRelationController {
    @Autowired
    private CategoryBrandRelationService relationService;

    @GetMapping("/brands/list")
    public R relationBrandsList(@RequestParam(value = "catId") Long catId) {
        List<CategoryBrandRelationEntity> brands = relationService.getRelationsByCatId(catId);
        List<BrandVO> brandVos = BeanUtils.transformFromInBatch(brands, BrandVO.class);

        return R.ok().put("data", brandVos);
    }

    @GetMapping("/catelog/list")
    @ApiOperation("获取品牌已关联的分类")
    public R categoryBrandRelationList(@RequestParam Long brandId) {
        List<CategoryBrandRelationEntity> list =
                relationService.getRelationsByBrandId(brandId);

        return R.ok().put("data", list);
    }

    @PostMapping("/save")
    @ApiOperation("新增管理关系")
    public R save(@RequestBody CategoryBrandRelationParam relationParam) {
        relationService.saveRelation(relationParam.getCatelogId(), relationParam.getBrandId());

        return R.ok();
    }

    @PostMapping("/delete")
    @ApiOperation("删除关联关系")
    public R delete(@RequestBody Long[] ids) {
        relationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
}
