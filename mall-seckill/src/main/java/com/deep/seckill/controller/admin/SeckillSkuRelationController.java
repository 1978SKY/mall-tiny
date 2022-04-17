package com.deep.seckill.controller.admin;

import com.deep.common.utils.PageUtils;
import com.deep.common.utils.R;
import com.deep.seckill.model.entity.SeckillSkuRelationEntity;
import com.deep.seckill.service.SeckillSkuRelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 每日秒杀关联商品
 *
 * @author Deep
 * @date 2022/4/17
 */
@Api(tags = "每日秒杀关联商品")
@RestController
@RequestMapping("/api/coupon/seckillskurelation")
public class SeckillSkuRelationController {
    private final SeckillSkuRelationService seckillSkuRelationService;

    public SeckillSkuRelationController(SeckillSkuRelationService seckillSkuRelationService) {
        this.seckillSkuRelationService = seckillSkuRelationService;
    }

    @RequestMapping("/list")
    @ApiOperation("关联商品列表")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = seckillSkuRelationService.queryPage(params);
        return R.ok().put("page", page);
    }

    @GetMapping("/info/{id}")
    @ApiOperation("信息")
    public R info(@PathVariable("id") Long id) {
        SeckillSkuRelationEntity seckillSkuRelation = seckillSkuRelationService.getById(id);

        return R.ok().put("seckillSkuRelation", seckillSkuRelation);
    }

    @RequestMapping("/save")
    @ApiOperation("新增")
    public R save(@RequestBody SeckillSkuRelationEntity seckillSkuRelation) {
        seckillSkuRelationService.save(seckillSkuRelation);

        return R.ok();
    }

    @RequestMapping("/update")
    @ApiOperation("删除")
    public R update(@RequestBody SeckillSkuRelationEntity seckillSkuRelation) {
        seckillSkuRelationService.updateById(seckillSkuRelation);

        return R.ok();
    }

    @RequestMapping("/delete")
    @ApiOperation("删除")
    public R delete(@RequestBody Long[] ids) {
        seckillSkuRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
}
