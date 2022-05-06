package com.deep.order.controller.web;

import com.deep.common.utils.R;
import com.deep.order.model.enume.GenerateOrderEnum;
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
@RequestMapping("/api/order/web")
public class OrderWebController {
    @Autowired
    private OrderWebService orderWebService;

    @GetMapping("/order.html")
    @ApiOperation("订单首页")
    public String listUserOderItems(
        @RequestParam(value = "pageNum", required = false, defaultValue = "1") String pageNum, Model model) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("pageNum", pageNum);
        List<OrderVO> orders = orderWebService.queryPage(params);

        model.addAttribute("orders", orders);
        return "order";
    }

    @GetMapping("/settlement.html")
    @ApiOperation("订单确认页")
    public String toTrade(Model model) {
        OrderConfirmVO orderConfirmVO = orderWebService.confirmOrder();

        model.addAttribute("confirmOrderData", orderConfirmVO);
        return "settlement";
    }

    @ResponseBody
    @PostMapping("/submitOrder")
    @ApiOperation("提交订单")
    public R submitOrder(@RequestBody OrderSubmitParam param) {
        GenerateOrderEnum orderEnum = orderWebService.submitOrder(param);
        log.info("订单生成日志 =======>> {}", orderEnum.getCode() + orderEnum.getMsg());
        if (orderEnum.getCode().equals(GenerateOrderEnum.SUCCESS_CREATE.getCode())) {
            return R.ok().put("sn", orderEnum.getMsg());
        }
        return R.error(orderEnum.getCode(), orderEnum.getMsg());
    }
}
