package com.deep.coupon.controller.admin;

import com.deep.common.utils.PageUtils;
import com.deep.common.utils.R;
import com.deep.coupon.model.entity.CouponHistoryEntity;
import com.deep.coupon.service.CouponHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 优惠券发放历史记录
 *
 * @author Deep
 * @date 2022/4/16
 */
@Api(tags = "优惠券发放历史记录")
@RestController
@RequestMapping("/api/coupon/couponhistory")
public class CouponHistoryController {

    private final CouponHistoryService couponHistoryService;

    public CouponHistoryController(CouponHistoryService couponHistoryService) {
        this.couponHistoryService = couponHistoryService;
    }

    @GetMapping("/list")
    @ApiOperation("优惠券")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = couponHistoryService.queryPage(params);

        return R.ok().put("page", page);
    }

    @GetMapping("/info/{id}")
    @ApiOperation("信息")
    public R info(@PathVariable("id") Long id){
        CouponHistoryEntity couponHistory = couponHistoryService.getById(id);

        return R.ok().put("couponHistory", couponHistory);
    }

    @PostMapping("/save")
    @ApiOperation("新增")
    public R save(@RequestBody CouponHistoryEntity couponHistory) {
        couponHistoryService.save(couponHistory);

        return R.ok();
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public R update(@RequestBody CouponHistoryEntity couponHistory) {
        couponHistoryService.updateById(couponHistory);

        return R.ok();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public R delete(@RequestBody Long[] ids) {
        couponHistoryService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
}
