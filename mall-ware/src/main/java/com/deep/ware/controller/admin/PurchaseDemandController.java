package com.deep.ware.controller.admin;

import com.deep.common.utils.PageUtils;
import com.deep.common.utils.R;
import com.deep.ware.model.entity.PurchaseDemandEntity;
import com.deep.ware.service.PurchaseDemandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 采购需求
 *
 * @author Deep
 * @date 2022/3/28
 */
@Api(tags = "采购需求—后台")
@RestController
@RequestMapping("/api/ware/purchasedetail")
public class PurchaseDemandController {
    @Autowired
    private PurchaseDemandService purchaseDemandService;

    @GetMapping("/list")
    @ApiOperation("获取采购需求列表")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = purchaseDemandService.queryPage(params);

        return R.ok().put("page", page);
    }

    @PostMapping("/save")
    @ApiOperation(value = "新增采购需求")
    public R save(@RequestBody PurchaseDemandEntity purchaseDetail) {
        purchaseDemandService.save(purchaseDetail);

        return R.ok();
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改采购需求")
    public R update(@RequestBody PurchaseDemandEntity purchase) {
        purchaseDemandService.updateById(purchase);

        return R.ok();
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除采购需求")
    public R delete(@RequestBody Long[] ids) {
        purchaseDemandService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
