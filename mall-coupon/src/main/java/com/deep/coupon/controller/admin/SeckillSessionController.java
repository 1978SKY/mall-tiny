package com.deep.coupon.controller.admin;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.deep.common.utils.PageUtils;
import com.deep.common.utils.R;
import com.deep.coupon.model.entity.SeckillSessionEntity;
import com.deep.coupon.model.vo.SeckillSessionWithSkusVO;
import com.deep.coupon.service.SeckillSessionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 每日秒杀
 *
 * @author Deep
 * @date 2022/4/16
 */
@Api(tags = "每日秒杀")
@RestController
@RequestMapping("/api/coupon/seckillsession")
public class SeckillSessionController {

    private final SeckillSessionService seckillSessionService;

    public SeckillSessionController(SeckillSessionService seckillSessionService) {
        this.seckillSessionService = seckillSessionService;
    }

    @GetMapping("/list")
    @ApiOperation("获取每日秒杀活动")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = seckillSessionService.queryPage(params);

        return R.ok().put("page", page);
    }

    @GetMapping("/info/{id}")
    @ApiOperation("信息")
    public R info(@PathVariable("id") Long id) {
        SeckillSessionEntity seckillSession = seckillSessionService.getById(id);

        return R.ok().put("seckillSession", seckillSession);
    }

    @PostMapping("/save")
    @ApiOperation("保存")
    public R save(@RequestBody SeckillSessionEntity seckillSession) {
        seckillSessionService.save(seckillSession);

        return R.ok();
    }

    @PostMapping("/update")
    @ApiOperation("修改")
    public R update(@RequestBody SeckillSessionEntity seckillSession) {
        seckillSessionService.updateById(seckillSession);

        return R.ok();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public R delete(@RequestBody Long[] ids) {
        seckillSessionService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    @GetMapping("/getSkillSessions")
    public R getSeckillSessions(@RequestParam(value = "day") int day) {
        List<SeckillSessionWithSkusVO> sessions = seckillSessionService.querySeckillSessions(day);

        return R.ok().put("data", sessions);
    }
}
