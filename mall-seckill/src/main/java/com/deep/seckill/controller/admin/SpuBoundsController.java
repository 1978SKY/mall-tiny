package com.deep.seckill.controller.admin;

import com.deep.common.utils.PageUtils;
import com.deep.common.utils.R;
import com.deep.seckill.model.entity.SpuBoundsEntity;
import com.deep.seckill.service.SpuBoundsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 商品spu积分
 *
 * @author Deep
 * @date 2022/4/16
 */
@Api(tags = "商品spu积分")
@RestController
@RequestMapping("/api/coupon/spubounds")
public class SpuBoundsController {
    private final SpuBoundsService spuBoundsService;

    public SpuBoundsController(SpuBoundsService spuBoundsService) {
        this.spuBoundsService = spuBoundsService;
    }

    @GetMapping("/list")
    @ApiOperation("商品spu积分")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = spuBoundsService.queryPage(params);

        return R.ok().put("page", page);
    }

    @PostMapping("/save")
    @ApiOperation("新增")
    public R save(@RequestBody SpuBoundsEntity spuBounds) {
        spuBoundsService.save(spuBounds);

        return R.ok();
    }

    @PostMapping("/update")
    @ApiOperation("修改")
    public R update(@RequestBody SpuBoundsEntity spuBounds) {
        spuBoundsService.updateById(spuBounds);

        return R.ok();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public R delete(@RequestBody Long[] ids) {
        spuBoundsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
}