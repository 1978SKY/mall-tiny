package com.deep.ware.controller.admin;

import com.deep.common.utils.PageUtils;
import com.deep.common.utils.R;
import com.deep.ware.service.WareSkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author Deep
 * @date 2022/3/28
 */
@Api(tags = "商品库存—后台")
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
    public R isHasStock(@RequestBody List<Long> skuIds) {
        Map<Long, Boolean> stockMap = wareSkuService.skuIdsHasStock(skuIds);

        return R.ok().put("data", stockMap);
    }

    @GetMapping("/lockInventory")
    @ApiOperation(value = "检查并锁定库存", hidden = true)
    public R checkAndLockStock(@RequestParam("skuId") Long skuId, @RequestParam("count") Integer count) {
        boolean b = wareSkuService.lockInventory(skuId, count);

        return b ? R.ok() : R.error("仓库没有该商品或库存不足!");
    }

    @PostMapping("/ckeckstock")
    @ApiOperation("检查并锁定库存")
    public R checkAndLock(@RequestBody Map<Long, Integer> stockMap) {

        boolean b = wareSkuService.lockInventory(stockMap);
        if (b) {
            return R.ok();
        }
        return R.error(-1, "仓库没有该商品或库存不足!");
    }

}
