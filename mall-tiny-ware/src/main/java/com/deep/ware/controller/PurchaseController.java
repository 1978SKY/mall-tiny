package com.deep.ware.controller;

import com.deep.common.utils.PageUtils;
import com.deep.common.utils.R;
import com.deep.ware.model.params.FinishParam;
import com.deep.ware.model.params.MergeParam;
import com.deep.ware.model.params.PurchaseParam;
import com.deep.ware.service.PurchaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 采购单
 *
 * @author Deep
 * @date 2022/3/28
 */
@Api(value = "采购单")
@RestController
@RequestMapping("/api/ware/purchase")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    @GetMapping("/list")
    @ApiOperation("获取采购单列表")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = purchaseService.queryPage(params);

        return R.ok().put("page", page);
    }

    @PostMapping("/save")
    @ApiOperation("新增采购单")
    public R save(@RequestBody PurchaseParam purchaseParam) {
        purchaseService.save(purchaseParam.convertTo());

        return R.ok();
    }

    @PostMapping("/update")
    @ApiOperation("更新采购单")
    public R update(@RequestBody PurchaseParam purchaseParam) {
        purchaseService.updateById(purchaseParam.convertTo());

        return R.ok();
    }

    @GetMapping("/unreceive/list")
    @ApiOperation("获取未分配采购单列表")
    public R unreceivedPurchases(@RequestParam Map<String, Object> params) {
        PageUtils page = purchaseService.queryUnreceivedPage(params);

        return R.ok().put("page", page);
    }

    @PostMapping("/merge")
    @ApiOperation("合并采购需求")
    public R mergePurchases(@RequestBody MergeParam mergeParam) {
        purchaseService.merge(mergeParam.getPurchaseId(), mergeParam.getItems());
        return R.ok();
    }

    @PostMapping("/done")
    public R finishPurchase(@RequestBody FinishParam finishParam) {

        purchaseService.finishPurchase(Arrays.asList(finishParam.getId()));
        return R.ok();
    }
}
