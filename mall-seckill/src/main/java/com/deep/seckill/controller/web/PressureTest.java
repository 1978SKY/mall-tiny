package com.deep.seckill.controller.web;

import com.deep.seckill.model.dto.RedisSkuDto;
import com.deep.seckill.service.SeckillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 压力测试
 *
 * @author Deep
 * @date 2022/5/9
 */
@Api(tags = "压力测试")
@RequestMapping("/api/seckill/test")
@RestController
public class PressureTest {
    private final SeckillService seckillService;

    public PressureTest(SeckillService seckillService) {
        this.seckillService = seckillService;
    }

    @ApiOperation(value = "压力测试", hidden = true)
    @GetMapping({"/item"})
    public RedisSkuDto skuItem(@RequestParam("sessionId") Long sessionId, @RequestParam("skuId") Long skuId) {

        return seckillService.getSeckillSkuDetail(sessionId, skuId);
    }
}
