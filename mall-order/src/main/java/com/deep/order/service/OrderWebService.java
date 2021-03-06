package com.deep.order.service;

import com.deep.order.model.enume.GenerateOrderEnum;
import com.deep.order.model.params.OrderSubmitParam;
import com.deep.order.model.vo.OrderConfirmVO;
import com.deep.order.model.vo.OrderVO;

import java.util.List;
import java.util.Map;

/**
 * 订单页
 *
 * @author Deep
 * @date 2022/4/5
 */
public interface OrderWebService {
    /**
     * 获取用户所有订单
     *
     * @param params 查询参数
     * @return 订单页
     */
    List<OrderVO> queryPage(Map<String, Object> params);

    /**
     * 订单确认页
     * @return 订单确认
     */
    OrderConfirmVO confirmOrder();

    /**
     * 提交订单
     *
     * @param submitParam 提交参数
     * @return 映射表
     */
    GenerateOrderEnum submitOrder(OrderSubmitParam submitParam);
}
