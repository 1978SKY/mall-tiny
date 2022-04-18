package com.deep.coupon.controller.admin;

import com.deep.common.utils.PageUtils;
import com.deep.common.utils.R;
import com.deep.coupon.model.entity.MemberPriceEntity;
import com.deep.coupon.service.MemberPriceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 商品会员价格
 *
 * @author Deep
 * @date 2022/4/16
 */
@Api(tags = "商品会员价格")
@RestController
@RequestMapping("/api/coupon/memberprice")
public class MemberPriceController {

    private final MemberPriceService memberPriceService;

    public MemberPriceController(MemberPriceService memberPriceService) {
        this.memberPriceService = memberPriceService;
    }

    @GetMapping("/list")
    @ApiOperation("获取商品会员价格列表")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = memberPriceService.queryPage(params);

        return R.ok().put("page", page);
    }

    @GetMapping("/info/{id}")
    @ApiOperation("信息")
    public R info(@PathVariable("id") Long id){
        MemberPriceEntity memberPrice = memberPriceService.getById(id);

        return R.ok().put("memberPrice", memberPrice);
    }

    @PostMapping("/save")
    @ApiOperation("新增")
    public R save(@RequestBody MemberPriceEntity memberPrice) {
        memberPriceService.save(memberPrice);

        return R.ok();
    }

    @PostMapping("/update")
    @ApiOperation("修改")
    public R update(@RequestBody MemberPriceEntity memberPrice) {
        memberPriceService.updateById(memberPrice);

        return R.ok();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public R delete(@RequestBody Long[] ids) {
        memberPriceService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
}
