package com.deep.order.controller.web;

import com.alipay.api.AlipayApiException;
import com.deep.order.component.AlipayTemplate;
import com.deep.order.model.vo.OrderVO;
import com.deep.order.model.vo.PayVO;
import com.deep.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 支付控制器
 *
 * @author Deep
 * @date 2022/4/7
 */
@Api(value = "支付控制器")
@Controller
@RequestMapping("/api/order/pay")
public class PayController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private AlipayTemplate alipayTemplate;

    @GetMapping(value = "/{orderSn}")
    @ApiOperation("支付页面")
    public String pay(@PathVariable("orderSn") String orderSn, Model model) {
        OrderVO orderVO = orderService.getOrderVO(orderSn);
        model.addAttribute("orderVO", orderVO);

        return "pay";
    }


    /**
     * 用户下单:支付宝支付<br>
     * 1、让支付页让浏览器展示<br>
     * 2、支付成功以后，跳转到用户的订单列表页<br>
     */
    @ResponseBody
    @ApiOperation("支付宝支付")
    @PostMapping(value = "/aliPayOrder")
    public String aliPayOrder(@RequestParam("orderSn") String orderSn) throws AlipayApiException {
        PayVO payVo = orderService.getOrderPay(orderSn);
        // 支付宝返回一个页面【支付宝账户登录的html页面】
        return alipayTemplate.pay(payVo);
    }
}
