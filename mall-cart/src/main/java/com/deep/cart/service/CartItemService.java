package com.deep.cart.service;

import com.deep.cart.model.vo.CartItemVO;

import java.util.List;

/**
 * 购物项
 *
 * @author Deep
 * @date 2022/4/5
 */
public interface CartItemService {
    /**
     * 更新选中状态
     *
     * @param skuId   商品id
     * @param checked 选中状态
     */
    void updateCheckStatus(Long skuId, Boolean checked);

    /**
     * 获取当前用户的购物车商品
     *
     * @return 商品集合
     */
    List<CartItemVO> getCheckItems();
}
