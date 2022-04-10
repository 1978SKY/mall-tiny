package com.deep.product.controller.admin;

import com.deep.common.utils.PageUtils;
import com.deep.common.utils.R;
import com.deep.product.model.entity.AttrGroupEntity;
import com.deep.product.model.params.AttrGroupParam;
import com.deep.product.model.params.AttrGroupRelationParam;
import com.deep.product.model.vo.AttrGroupWithAttrsVO;
import com.deep.product.model.vo.AttrVO;
import com.deep.product.service.admin.AttrAttrgroupRelationService;
import com.deep.product.service.admin.AttrGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
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

    @Autowired
    private AttrAttrgroupRelationService relationService;

    @GetMapping("/list/{catelogId}")
    @ApiOperation("获取分类关联属性分组")
    public R list(@RequestParam Map<String, Object> params,
                  @PathVariable(value = "catelogId") Long categoryId) {
        PageUtils page = attrGroupService.queryPage(params, categoryId);

        return R.ok().put("page", page);
    }

    @PostMapping("/save")
    @ApiOperation("新增")
    public R save(@RequestBody AttrGroupParam param) {
        AttrGroupEntity entity = param.convertTo();
        attrGroupService.save(entity);

        return R.ok();
    }

    @PostMapping("/update")
    @ApiOperation("修改")
    public R update(@RequestBody AttrGroupParam param) {
        AttrGroupEntity entity = param.convertTo();
        attrGroupService.updateById(entity);

        return R.ok();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public R delete(@RequestBody Long[] attrGroupIds) {
        attrGroupService.deleteBatch(Arrays.asList(attrGroupIds));

        return R.ok();
    }

    @PostMapping("/attr/relation")
    @ApiOperation("新增关联")
    public R addRelation(@RequestBody List<AttrGroupRelationParam> params) {
        relationService.saveRelation(params);

        return R.ok();
    }

    @PostMapping("/attr/relation/delete")
    @ApiOperation("移除关联")
    public R deleteRelation(@RequestBody List<AttrGroupRelationParam> params) {
        relationService.deleteRelations(params);

        return R.ok();
    }


    @GetMapping("/{attrgroupId}/attr/relation")
    @ApiOperation("获取已关联属性")
    public R attrRelation(@PathVariable("attrgroupId") Long groupId) {
        List<AttrVO> attrVOS = relationService.getAttrsByGroupId(groupId);

        return R.ok().put("data", attrVOS);
    }

    @GetMapping("/{attrgroupId}/noattr/relation")
    @ApiOperation("获取未关联属性")
    public R attrNoRelation(@RequestParam Map<String, Object> params,
                            @PathVariable("attrgroupId") Long groupId) {
        PageUtils page = relationService.getNoRelationAttrPage(params, groupId);
        return R.ok().put("data", page);
    }

    @GetMapping("/{catelogId}/withattr")
    @ApiOperation("获取分类关联的属性分组")
    public R getAttrGroupWithAttrs(@PathVariable(value = "catelogId") Long catId) {
        List<AttrGroupWithAttrsVO> vos = attrGroupService.getAttrGroupWithAttrs(catId);

        return R.ok().put("data", vos);
    }

}
