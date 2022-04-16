package com.deep.order.controller.web;

import com.deep.order.model.params.OrderSubmitParam;
import com.deep.order.model.vo.OrderConfirmVO;
import com.deep.order.model.vo.OrderVO;
import com.deep.order.service.OrderWebService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单
 *
 * @author Deep
 * @date 2022/4/5
 */
@Slf4j
@Controller
@Api(tags = "订单-前台")
@RequestMapping("/api/order/index")
public class OrderWebController {
    @Autowired
    private OrderWebService orderWebService;

    @GetMapping("/")
    @ApiOperation("订单首页")
    public String listUserOderItems(@RequestParam(value = "pageNum",
            required = false, defaultValue = "1") String pageNum, Model model) {
        Map<String, Object> params = new HashMap<>();
        params.put("pageNum", pageNum);
        List<OrderVO> orders = orderWebService.queryPage(params);

        model.addAttribute("orders", orders);
        return "order";
    }

    @GetMapping("/settlement")
    @ApiOperation("订单结算页")
    public String toTrade(Model model) {
        long start = System.currentTimeMillis();
        OrderConfirmVO orderConfirmVO = orderWebService.confirmOrder();
        long end = System.currentTimeMillis();
        log.info("远程查询商品共耗时{}ms", (end - start));

        model.addAttribute("confirmOrderData", orderConfirmVO);
        return "settlement";
    }

    @ResponseBody
    @PostMapping("/submitOrder")
    @ApiOperation("提交订单")
    public String submitOrder(@RequestBody OrderSubmitParam param) {
        long start = System.currentTimeMillis();
        Map<Integer, String> map = orderWebService.submitOrder(param);
        long end = System.currentTimeMillis();
        log.info("生成订单共耗时{}ms", (end - start));

        if (map == null || map.size() != 1) {
            throw new RuntimeException("orderWebService内部逻辑错误!");
        }
        if (map.containsKey(-1)) {
            return "order token 失效!";
        }
        return map.get(1);
    }
}
