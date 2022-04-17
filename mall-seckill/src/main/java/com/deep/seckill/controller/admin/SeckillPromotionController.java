package com.deep.seckill.controller.admin;

import com.deep.common.utils.PageUtils;
import com.deep.common.utils.R;
import com.deep.seckill.model.entity.SeckillPromotionEntity;
import com.deep.seckill.service.SeckillPromotionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 秒杀活动
 *
 * @author Deep
 * @date 2022/4/16
 */
@Api(tags = "秒杀活动")
@RestController
@RequestMapping("/api/coupon/seckillpromotion")
public class SeckillPromotionController {
    private final SeckillPromotionService seckillPromotionService;

    public SeckillPromotionController(SeckillPromotionService seckillPromotionService) {
        this.seckillPromotionService = seckillPromotionService;
    }

    @GetMapping("/list")
    @ApiOperation("秒杀活动")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = seckillPromotionService.queryPage(params);

        return R.ok().put("page", page);
    }

    @GetMapping("/info/{id}")
    @ApiOperation("信息")
    public R info(@PathVariable("id") Long id){
        SeckillPromotionEntity seckillPromotion = seckillPromotionService.getById(id);

        return R.ok().put("seckillPromotion", seckillPromotion);
    }

    @PostMapping("/save")
    @ApiOperation("新增")
    public R save(@RequestBody SeckillPromotionEntity seckillPromotion) {
        seckillPromotionService.save(seckillPromotion);

        return R.ok();
    }

    @PostMapping("/update")
    @ApiOperation("修改")
    public R update(@RequestBody SeckillPromotionEntity seckillPromotion) {
        seckillPromotionService.updateById(seckillPromotion);

        return R.ok();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public R delete(@RequestBody Long[] ids) {
        seckillPromotionService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
}
