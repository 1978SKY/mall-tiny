package com.deep.order.controller.web;

import com.deep.common.utils.BeanUtils;
import com.deep.common.utils.PageUtils;
import com.deep.order.model.params.OrderSubmitParam;
import com.deep.order.model.vo.OrderConfirmVO;
import com.deep.order.model.vo.OrderVO;
import com.deep.order.service.OrderWebService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单
 *
 * @author Deep
 * @date 2022/4/5
 */
@Controller
@RequestMapping("/api/order/index")
public class OrderWebController {
    @Autowired
    private OrderWebService orderWebService;

    @GetMapping("/")
    @ApiOperation("订单首页")
    public String listUserOderItems(@RequestParam(value = "pageNum", required = false, defaultValue = "1") String pageNum, Model model) {
        Map<String, Object> params = new HashMap<>();
        params.put("pageNum", pageNum);
        List<OrderVO> orders = orderWebService.queryPage(params);

        model.addAttribute("orders", orders);
        return "orderIndex";
    }

    @GetMapping("/settlement")
    @ApiOperation("订单结算页")
    public String toTrade(Model model) {
        OrderConfirmVO orderConfirmVO = orderWebService.confirmOrder();
        model.addAttribute("confirmOrderData", orderConfirmVO);
        return "settlement";
    }

    @PostMapping("/submitOrder")
    @ApiOperation("提交订单")
    public String submitOrder(OrderSubmitParam param, Model model) {
        Map<Integer, OrderVO> map = orderWebService.submitOrder(param);
        if (map == null || map.size() != 1) {
            throw new RuntimeException("orderWebService内部逻辑错误!");
        }
        if (map.containsKey(-1)) {
            return "order token 失效!";
        }
        OrderVO orderVO = BeanUtils.transformFrom(map.get(1), OrderVO.class);
        model.addAttribute("orderVO", orderVO);
        return "pay";
    }
}
