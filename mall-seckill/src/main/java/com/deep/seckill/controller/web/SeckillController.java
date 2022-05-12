package com.deep.seckill.controller.web;

import com.deep.seckill.model.dto.RedisSkuDto;
import com.deep.seckill.model.enume.DeductionEnums;
import com.deep.seckill.model.params.DoSeckillParam;
import com.deep.seckill.model.vo.SessionLocalSkuVo;
import com.deep.seckill.service.SeckillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 秒杀
 *
 * @author Deep
 * @date 2022/4/22
 */
@Api(tags = "秒杀")
@Controller
@RequestMapping("/api/seckill")
public class SeckillController {

    private final SeckillService seckillService;

    public SeckillController(SeckillService seckillService) {
        this.seckillService = seckillService;
    }

    @ApiOperation(value = "秒杀商品首页")
    @GetMapping("/seckill.html")
    public String index(Model model) {
        List<SessionLocalSkuVo.LocalSkuVo> skus = seckillService.getSeckillSessions();
        model.addAttribute("seckillSkus", skus);
        return "seckill";
    }

    @ApiOperation(value = "秒杀商品详细信息")
    @GetMapping({"/item.html"})
    public String skuItem(@RequestParam("sessionId") Long sessionId, @RequestParam("skuId") Long skuId, Model model) {
        RedisSkuDto sku = seckillService.getSeckillSkuDetail(sessionId, skuId);
        model.addAttribute("product", sku);
        return "item";
    }



    @ApiOperation(value = "进行秒杀")
    @ResponseBody
    @PostMapping("/do/seckill")
    public String doSeckill(@RequestBody DoSeckillParam seckillParam) {
        Map<Boolean, String> seckill = seckillService.doSeckill(seckillParam);

        return seckill.containsKey(true) ? seckill.get(true) : seckill.get(false);
    }


}
