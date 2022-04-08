package com.deep.order.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.common.utils.BeanUtils;
import com.deep.common.utils.PageUtils;
import com.deep.common.utils.Query;
import com.deep.order.dao.OrderDao;
import com.deep.order.model.entity.OrderEntity;
import com.deep.order.model.entity.OrderItemEntity;
import com.deep.order.model.vo.OrderItemVO;
import com.deep.order.model.vo.OrderVO;
import com.deep.order.model.vo.PayVO;
import com.deep.order.service.OrderItemService;
import com.deep.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 订单
 *
 * @author Deep
 * @date 2022/3/29
 */
@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {
    @Autowired
    private OrderItemService orderItemService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<OrderEntity> wrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (StringUtils.hasLength(key)) {
            wrapper.eq("order_sn", key).or()
                    .eq("member_id", key).or()
                    .like("member_username", key);
        }
        IPage<OrderEntity> page =
                this.page(new Query<OrderEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }

    @Override
    public List<OrderVO> queryUserOrderPage(Long userId, Map<String, Object> params) {
        Assert.notNull(userId, "用户id不能为空!");
        QueryWrapper<OrderEntity> wrapper = new QueryWrapper<>();

        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                wrapper.eq("member_id", userId).orderByDesc("create_time")
        );
        List<OrderVO> orderVOS = BeanUtils.transformFromInBatch(page.getRecords(), OrderVO.class);

        List<Long> orderIds = orderVOS.stream().map(OrderVO::getId).collect(Collectors.toList());
        List<OrderItemEntity> items = orderItemService.getByOrderIds(orderIds);
        List<OrderItemVO> itemVOS = BeanUtils.transformFromInBatch(items, OrderItemVO.class);
        // key:orderId value:List<OrderVO>
        Map<Long, List<OrderItemVO>> itemsMap = new HashMap<>();
        for (OrderItemVO itemVO : itemVOS) {
            List<OrderItemVO> list;
            if (!itemsMap.containsKey(itemVO.getSkuId())) {
                list = new ArrayList<>();
                list.add(itemVO);
            } else {
                list = itemsMap.get(itemVO.getSkuId());
            }
            list.add(itemVO);
            itemsMap.put(itemVO.getSkuId(), list);
        }

        orderVOS.forEach(item -> {
            item.setItems(itemsMap.get(item.getId()));
        });
        return orderVOS;
    }

    @Override
    public PayVO getOrderPay(String orderSn) {
        Assert.hasLength(orderSn, "订单号不能为空!");

        PayVO payVO = new PayVO();
        QueryWrapper<OrderEntity> wrapper = new QueryWrapper<>();
        OrderEntity order = getOne(wrapper.eq("order_sn", orderSn));
        BigDecimal payAmount = order.getPayAmount().setScale(2, BigDecimal.ROUND_UP);
        payVO.setTotal_amount(payAmount.toString());
        payVO.setOut_trade_no(order.getOrderSn());
        payVO.setSubject(order.getOrderSn());
        payVO.setBody("");

        return payVO;
    }
}
