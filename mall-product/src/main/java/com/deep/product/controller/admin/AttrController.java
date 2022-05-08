package com.deep.product.controller.admin;

import com.deep.common.utils.PageUtils;
import com.deep.common.utils.R;
import com.deep.product.model.entity.ProductAttrValueEntity;
import com.deep.product.model.params.AttrParam;
import com.deep.product.model.vo.AttrVO;
import com.deep.product.service.AttrService;
import com.deep.product.service.ProductAttrValueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 商品属性规格
 *
 * @author Deep
 * @date 2022/3/18
 */
@Api(tags = "商品属性规格")
@RestController
@RequestMapping("/api/product/attr")
public class AttrController {

    @Autowired
    private AttrService attrService;
    @Autowired
    private ProductAttrValueService productAttrValueService;

    @ApiOperation("获取属性规格")
    @GetMapping("/{attrType}/list/{catelogId}")
    public R baseAttrList(@RequestParam Map<String, Object> params, @PathVariable("catelogId") Long catId,
                          @PathVariable("attrType") String type) {
        PageUtils page = attrService.queryAttrPage(params, catId, type);
        return R.ok().put("page", page);
    }

    @ApiOperation("获取属性详情")
    @GetMapping("/info/{attrId}")
    public R info(@PathVariable("attrId") Long attrId) {
        AttrVO attrVO = attrService.getAttrInfo(attrId);

        return R.ok().put("attr", attrVO);
    }

    @ApiOperation("获取spuId对应的基础属性")
    @GetMapping("/base/listforspu/{spuId}")
    public R baseAttrListForSpu(@PathVariable("spuId") Long spuId) {
        List<ProductAttrValueEntity> attrs = productAttrValueService.baseAttrsForSpu(spuId);

        return R.ok().put("data", attrs);
    }

    @ApiOperation("保存规格属性")
    @PostMapping("/save")
    public R save(@RequestBody AttrParam attrParam) {
        attrService.saveAttr(attrParam);

        return R.ok();
    }

    @ApiOperation("删除属性")
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] attrIds) {
        attrService.deleteAttrs(Arrays.asList(attrIds));

        return R.ok();
    }

    @PostMapping("/update")
    @ApiOperation("更新属性")
    public R update(@RequestBody AttrParam attrParam) {
        attrService.updateAttr(attrParam);
        return R.ok();
    }

}
