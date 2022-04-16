package com.deep.cart.service;

import com.deep.cart.model.vo.CartItemVO;
import com.deep.cart.model.vo.CartVO;
import org.springframework.lang.NonNull;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * 购物车
 *
 * @author Deep
 * @date 2022/4/3
 */
public interface CartService {

    /**
     * 获取购物车数据
     *
     * @return 购物车
     */
    CartVO getCartDetail();

    /**
     * 添加到购物车
     *
     * @param skuId 商品id
     * @param count 商品数量
     */
    void addToCart(@NonNull Long skuId, int count);

    /**
     * 获取添加到购物车中的商品
     *
     * @param skuId 商品id
     * @return 商品
     */
    CartItemVO getCartItem(@Nonnull Long skuId);

    /**
     * 更新商品选中状态
     *
     * @param skuId   商品id
     * @param checked 选中状态
     */
    void updateCheckStatus(Long skuId, Boolean checked);

    /**
     * 获取购物项数据
     *
     * @return 购物项集合
     */
    List<CartItemVO> getCartItems();
}
