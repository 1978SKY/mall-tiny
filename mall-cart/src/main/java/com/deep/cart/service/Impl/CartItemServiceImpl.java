package com.deep.cart.service.Impl;

import com.alibaba.fastjson.JSON;
import com.deep.cart.interceptor.LoginInterceptor;
import com.deep.cart.model.constant.CartConstant;
import com.deep.cart.model.vo.CartItemVO;
import com.deep.cart.model.vo.CartVO;
import com.deep.cart.service.CartItemService;
import com.deep.cart.service.CartService;
import com.deep.common.model.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 购物项
 *
 * @author Deep
 * @date 2022/4/5
 */
@Service("cartItemService")
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private CartService cartService;

    @Override
    public void updateCheckStatus(Long skuId, Boolean checked) {
        Assert.notNull(skuId, "商品id不能为空!");
        cartService.updateCheckStatus(skuId, checked);
    }

    @Override
    public List<CartItemVO> getCheckItems() {
        return cartService.getCartItems().stream().filter(CartItemVO::getCheck).collect(Collectors.toList());
    }

    @Override
    public boolean deleteItems(@NonNull List<Long> skuIds) {
        Assert.notEmpty(skuIds, "商品id集合不能为空!");
        Long userId = LoginInterceptor.LOGIN_USER_THREAD_LOCAL.get().getId();
        String key = CartConstant.CART_PREFIX + userId;
        BoundHashOperations<String, String, String> ops = redisTemplate.boundHashOps(key);
        skuIds.parallelStream().forEach(skuId -> {
            if (skuId != null) {
                ops.delete(skuId.toString());
            }
        });
        return true;
    }

    /**
     * 绑定Redis hash操作
     */
    private BoundHashOperations<String, Object, Object> getCartOps() {
        MemberDTO member = LoginInterceptor.LOGIN_USER_THREAD_LOCAL.get();
        // 当前用户信息
        String cartKey = CartConstant.CART_PREFIX + member.getId();

        return redisTemplate.boundHashOps(cartKey);
    }
}
