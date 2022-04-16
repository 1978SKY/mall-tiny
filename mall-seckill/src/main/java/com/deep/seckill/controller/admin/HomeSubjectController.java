package com.deep.seckill.controller.admin;

import com.deep.common.utils.PageUtils;
import com.deep.common.utils.R;
import com.deep.seckill.model.entity.HomeSubjectEntity;
import com.deep.seckill.service.HomeSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】
 *
 * @author Deep
 * @date 2022/4/16
 */
@Api(tags = "首页专题表")
@RestController
@RequestMapping("/api/coupon/homesubject")
public class HomeSubjectController {
    private final HomeSubjectService homeSubjectService;

    public HomeSubjectController(HomeSubjectService homeSubjectService) {
        this.homeSubjectService = homeSubjectService;
    }

    @GetMapping("/list")
    @ApiOperation("列表")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = homeSubjectService.queryPage(params);

        return R.ok().put("page", page);
    }

    @PostMapping("/save")
    @ApiOperation("保存")
    public R save(@RequestBody HomeSubjectEntity homeSubject) {
        homeSubjectService.save(homeSubject);

        return R.ok();
    }

    @PostMapping("/update")
    @ApiOperation("修改")
    public R update(@RequestBody HomeSubjectEntity homeSubject) {
        homeSubjectService.updateById(homeSubject);

        return R.ok();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public R delete(@RequestBody Long[] ids) {
        homeSubjectService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
}
