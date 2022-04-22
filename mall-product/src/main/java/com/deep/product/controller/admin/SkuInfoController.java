package com.deep.product.controller.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.deep.common.utils.PageUtils;
import com.deep.common.utils.R;
import com.deep.product.model.entity.SkuInfoEntity;
import com.deep.product.service.admin.SkuInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * sku信息
 *
 * @author Deep
 * @date 2022/3/20
 */
@Api(tags = "sku信息")
@RestController
@RequestMapping("/api/product/skuinfo")
public class SkuInfoController {
    @Autowired
    private SkuInfoService skuInfoService;

    @GetMapping("/list")
    @ApiOperation("获取sku页面")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = skuInfoService.queryPage(params);

        return R.ok().put("page", page);
    }

    @GetMapping("/info/{skuId}")
    @ApiOperation("获取指定商品信息")
    public R info(@PathVariable("skuId") Long skuId) {
        SkuInfoEntity skuInfo = skuInfoService.getById(skuId);

        return R.ok().put("skuInfo", skuInfo);
    }

    @GetMapping(value = "/saleAttr/{skuId}")
    public R getSkuSaleAttrValues(@PathVariable("skuId") Long skuId) {
        List<String> saleAttrs = skuInfoService.getSkuSaleAttr(skuId);
        return R.ok().put("saleAttrs", saleAttrs);
    }

    @PostMapping("/info/skuIds")
    @ApiOperation("批量获取商品")
    public R infos(@RequestBody List<Long> skuIds) {
        List<SkuInfoEntity> skuInfos = skuInfoService.listByIds(skuIds);

        return R.ok().put("data", skuInfos);
    }
}
