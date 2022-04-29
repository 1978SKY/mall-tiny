package com.deep.order.controller.admin;

import com.deep.common.model.dto.OrderDto;
import com.deep.common.utils.BeanUtils;
import com.deep.common.utils.PageUtils;
import com.deep.common.utils.R;
import com.deep.order.model.entity.OrderEntity;
import com.deep.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 订单控制器
 *
 * @author Deep
 * @date 2022/3/29
 */
@Slf4j
@Api(tags = "订单-后台")
@RestController
@RequestMapping("/api/order/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    @ApiOperation("获取订单列表")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = orderService.queryPage(params);

        return R.ok().put("page", page);
    }

    @GetMapping("/{memberId}/orders")
    @ApiOperation("获取用户订单")
    public R getMemberOrders(@PathVariable("memberId") Long memberId) {
        List<OrderEntity> orders = orderService.getMemberOrders(memberId);
        List<OrderDto> orderDtos = BeanUtils.transformFromInBatch(orders, OrderDto.class);

        return R.ok().put("data", orderDtos);
    }
}
