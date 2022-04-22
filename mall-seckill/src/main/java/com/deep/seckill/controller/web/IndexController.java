package com.deep.seckill.controller.web;

import java.util.ArrayList;
import java.util.List;

import com.deep.seckill.model.dto.SeckillSkuDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.deep.common.utils.R;
import com.deep.seckill.model.dto.SeckillSessionWithSkusDTO;
import com.deep.seckill.service.IndexService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 秒杀首页
 * 
 * @author Deep
 * @date 2022/4/21
 */
@Slf4j
@Api(tags = "秒杀首页")
@RestController
@RequestMapping("/api/seckill")
public class IndexController {
    private final IndexService indexService;

    public IndexController(IndexService indexService) {
        this.indexService = indexService;
    }

    @ApiOperation("获取秒杀商品")
    @GetMapping("/skus")
    public R getSeckillSkus() {
        List<SeckillSkuDTO> skus = indexService.getSeckillSessions();
        return R.ok().put("data", skus);
    }
}
