package com.deep.order.controller.admin;

import com.deep.common.utils.PageUtils;
import com.deep.common.utils.R;
import com.deep.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 订单控制器
 *
 * @author Deep
 * @date 2022/3/29
 */
@Api(value = "订单控制器")
@RestController
@RequestMapping("/api/order/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping("/list")
    @ApiOperation("获取订单列表")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = orderService.queryPage(params);

        return R.ok().put("page", page);
    }
}
