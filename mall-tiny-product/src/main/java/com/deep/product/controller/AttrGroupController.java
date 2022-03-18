package com.deep.product.controller;

import com.deep.common.utils.PageUtils;
import com.deep.common.utils.R;
import com.deep.product.model.entity.AttrGroupEntity;
import com.deep.product.model.params.AttrGroupParam;
import com.deep.product.service.AttrGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 属性分组
 *
 * @author Deep
 * @date 2022/3/17
 */
@Api(value = "属性分组")
@RestController
@RequestMapping("/api/product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    @GetMapping("/list/{catelogId}")
    @ApiOperation("获取分类关联属性分组")
    public R list(@RequestParam Map<String, Object> params,
                  @PathVariable(value = "catelogId") Long categoryId) {
        PageUtils page = attrGroupService.queryPage(params, categoryId);

        return R.ok().put("page", page);
    }

    @RequestMapping("/save")
    @ApiOperation("新增属性分组")
    public R save(@RequestBody AttrGroupParam param) {
        AttrGroupEntity entity = param.convertTo();
        attrGroupService.save(entity);

        return R.ok();
    }


}
