package com.deep.seckill.controller.web;

import com.deep.seckill.model.dto.SeckillSkuDTO;
import com.deep.seckill.service.IndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Thymeleaf回应页
 * 
 * @author Deep
 * @date 2022/4/22
 */
@Api(tags = "秒杀首页", hidden = true)
@Controller
@RequestMapping("/api/seckill/")
public class ThIndexController {

    private final IndexService indexService;

    public ThIndexController(IndexService indexService) {
        this.indexService = indexService;
    }

    @ApiOperation(value = "获取秒杀商品", hidden = true)
    @GetMapping("/index.html")
    public String index(Model model) {
        List<SeckillSkuDTO> skus = indexService.getSeckillSessions();
        model.addAttribute("seckillSkus", skus);
        return "seckill";
    }

    @GetMapping({"/{skuId}.html"})
    public String skuItem(@PathVariable("skuId") Long skuId, Model model) {
        indexService.getSeckillSkuDetail(skuId);

        return null;
    }
}
