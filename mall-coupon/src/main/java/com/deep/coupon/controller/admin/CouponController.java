package com.deep.coupon.controller.admin;

import com.deep.common.utils.PageUtils;
import com.deep.common.utils.R;
import com.deep.coupon.model.entity.CouponEntity;
import com.deep.coupon.service.CouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 优惠券
 *
 * @author Deep
 * @date 2022/4/16
 */
@Api(tags = "优惠券")
@RestController
@RequestMapping("/api/coupon/coupon")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @GetMapping("/list")
    @ApiOperation("优惠券")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = couponService.queryPage(params);

        return R.ok().put("page", page);
    }

    @GetMapping("/info/{id}")
    @ApiOperation("信息")
    public R info(@PathVariable("id") Long id) {
        CouponEntity coupon = couponService.getById(id);

        return R.ok().put("coupon", coupon);
    }

    @PostMapping("/save")
    @ApiOperation("新增")
    public R save(@RequestBody CouponEntity coupon) {
        couponService.save(coupon);

        return R.ok();
    }

    @PostMapping("/update")
    @ApiOperation("修改")
    public R update(@RequestBody CouponEntity coupon) {
        couponService.updateById(coupon);

        return R.ok();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public R delete(@RequestBody Long[] ids) {
        couponService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
}
