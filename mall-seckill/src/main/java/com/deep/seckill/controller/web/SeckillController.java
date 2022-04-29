package com.deep.seckill.controller.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.deep.seckill.model.dto.RedisSkuDto;
import com.deep.seckill.model.vo.SessionLocalSkuVo;
import com.deep.seckill.service.IndexService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 秒杀
 * 
 * @author Deep
 * @date 2022/4/22
 */
@Api(tags = "秒杀")
@Controller
@RequestMapping("/api/seckill/")
public class SeckillController {

    private final IndexService indexService;

    public SeckillController(IndexService indexService) {
        this.indexService = indexService;
    }

    @ApiOperation(value = "秒杀商品首页")
    @GetMapping("/seckill.html")
    public String index(Model model) {
        List<SessionLocalSkuVo.LocalSkuVo> skus = indexService.getSeckillSessions();
        model.addAttribute("seckillSkus", skus);
        return "seckill";
    }

    @ApiOperation(value = "秒杀商品详细信息")
    @GetMapping({"/item.html"})
    public String skuItem(@RequestParam("sessionId") Long sessionId, @RequestParam("skuId") Long skuId, Model model) {
        RedisSkuDto sku = indexService.getSeckillSkuDetail(sessionId, skuId);
        model.addAttribute("product", sku);
        return "item";
    }
}
