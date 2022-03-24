package com.deep.product.controller.admin;

import com.deep.common.utils.PageUtils;
import com.deep.common.utils.R;
import com.deep.product.model.entity.ProductAttrValueEntity;
import com.deep.product.model.vo.AttrVO;
import com.deep.product.service.AttrService;
import com.deep.product.service.ProductAttrValueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 商品属性规格
 *
 * @author Deep
 * @date 2022/3/18
 */
@Api(value = "商品属性规格")
@RestController
@RequestMapping("/api/product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;
    @Autowired
    private ProductAttrValueService productAttrValueService;

    @GetMapping("/{attrType}/list/{catelogId}")
    @ApiOperation("查询属性规格")
    public R baseAttrList(@RequestParam Map<String, Object> params,
                          @PathVariable("catelogId") Long catId,
                          @PathVariable("attrType") String type) {

        PageUtils page = attrService.queryAttrPage(params, catId, type);
        return R.ok().put("page", page);
    }

    @GetMapping("/info/{attrId}")
    @ApiOperation("获取指定属性详情")
    public R info(@PathVariable("attrId") Long attrId) {
        AttrVO respVO = attrService.getAttrInfo(attrId);

        return R.ok().put("attr", respVO);
    }

    @GetMapping("/base/listforspu/{spuId}")
    @ApiOperation("获取spuId对应的基础属性")
    public R baseAttrListForSpu(@PathVariable("spuId") Long spuId) {
        List<ProductAttrValueEntity> attrs = productAttrValueService.baseAttrsForSpu(spuId);

        return R.ok().put("data", attrs);
    }
}
