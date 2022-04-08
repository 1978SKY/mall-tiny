package com.deep.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deep.common.utils.PageUtils;
import com.deep.order.model.entity.OrderEntity;
import com.deep.order.model.vo.OrderVO;
import com.deep.order.model.vo.PayVO;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

/**
 * 订单
 *
 * @author Deep
 * @date 2022/3/29
 */
public interface OrderService extends IService<OrderEntity> {
    /**
     * 获取订单列表
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询用户订单
     *
     * @param userId 用户id
     * @param params 查询参数
     * @return 订单列表
     */
    List<OrderVO> queryUserOrderPage(@Nonnull Long userId, Map<String, Object> params);

    /**
     * 获取订单支付
     *
     * @param orderSn 订单号
     * @return 订单
     */
    PayVO getOrderPay(@Nonnull String orderSn);
}
