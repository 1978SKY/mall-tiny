package com.deep.coupon.controller.admin;

import com.deep.common.utils.PageUtils;
import com.deep.common.utils.R;
import com.deep.coupon.model.entity.SkuFullReductionEntity;
import com.deep.coupon.service.SkuFullReductionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 商品满减信息
 *
 * @author Deep
 * @date 2022/4/16
 */
@Api(tags = "商品满减信息")
@RestController
@RequestMapping("/api/coupon/skufullreduction")
public class SkuFullReductionController {
    private final SkuFullReductionService skuFullReductionService;

    public SkuFullReductionController(SkuFullReductionService skuFullReductionService) {
        this.skuFullReductionService = skuFullReductionService;
    }

    @GetMapping("/list")
    @ApiOperation("满减信息列表")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = skuFullReductionService.queryPage(params);

        return R.ok().put("page", page);
    }

    @GetMapping("/info/{id}")
    @ApiOperation("信息")
    public R info(@PathVariable("id") Long id){
        SkuFullReductionEntity skuFullReduction = skuFullReductionService.getById(id);

        return R.ok().put("skuFullReduction", skuFullReduction);
    }

    @PostMapping("/save")
    @ApiOperation("新增")
    public R save(@RequestBody SkuFullReductionEntity skuFullReduction) {
        skuFullReductionService.save(skuFullReduction);

        return R.ok();
    }

    @PostMapping("/update")
    @ApiOperation("修改")
    public R update(@RequestBody SkuFullReductionEntity skuFullReduction) {
        skuFullReductionService.updateById(skuFullReduction);

        return R.ok();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public R delete(@RequestBody Long[] ids) {
        skuFullReductionService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
