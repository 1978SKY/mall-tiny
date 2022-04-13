package com.deep.ware.controller.admin;

import com.deep.common.utils.PageUtils;
import com.deep.common.utils.R;
import com.deep.ware.service.WareSkuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author Deep
 * @date 2022/3/28
 */
@RestController
@RequestMapping("/api/ware/waresku")
public class WareSkuController {
    @Autowired
    private WareSkuService wareSkuService;

    @GetMapping("/list")
    @ApiOperation("商品库存列表")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = wareSkuService.queryPage(params);

        return R.ok().put("page", page);
    }

    @PostMapping("/hasStock")
    @ApiOperation("判断是否有商品库存")
    R isHasStock(@RequestBody List<Long> skuIds) {
        Map<Long, Boolean> stockMap = wareSkuService.skuIdsHasStock(skuIds);

        return R.ok().put("data", stockMap);
    }
}