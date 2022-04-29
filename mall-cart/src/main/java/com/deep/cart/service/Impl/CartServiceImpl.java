package com.deep.cart.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.deep.cart.feign.ProductFeignService;
import com.deep.cart.interceptor.LoginInterceptor;
import com.deep.cart.model.constant.CartConstant;
import com.deep.cart.model.vo.CartItemVO;
import com.deep.cart.model.vo.CartVO;
import com.deep.cart.service.CartService;
import com.deep.common.exception.FeignRequestException;
import com.deep.common.model.dto.MemberDTO;
import com.deep.common.model.dto.SkuInfoDTO;
import com.deep.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 购物车
 *
 * @author Deep
 * @date 2022/4/3
 */
@Service("cartService")
public class CartServiceImpl implements CartService {
    @Autowired
    private ProductFeignService productFeignService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public CartVO getCartDetail() {
        // 获取购物项数据
        List<CartItemVO> items = getCartItems();
        // 保存到购物车
        CartVO cart = new CartVO();
        cart.setItems(items);
        return cart;
    }

    @Override
    public void addToCart(Long skuId, int count) {
        Assert.notNull(skuId, "商品id不能为空!");
        BoundHashOperations<String, Object, Object> ops = getCartOps();
        // Query product form redis
        String productRedis = (String) ops.get(skuId.toString());
        if (!StringUtils.hasLength(productRedis)) {
            // Redis doesn't has the product
            createAndAdd(ops, skuId, count);
        } else {
            // Update the product to redis
            CartItemVO itemVO = JSON.parseObject(productRedis, CartItemVO.class);
            itemVO.setCount(itemVO.getCount() + count);
            ops.put(skuId.toString(), JSON.toJSONString(itemVO));
        }
    }

    @Override
    public CartItemVO getCartItem(@NonNull Long skuId) {
        Assert.notNull(skuId, "商品id不能为空!");

        BoundHashOperations<String, Object, Object> ops = getCartOps();
        String json = (String) ops.get(skuId.toString());

        return JSON.parseObject(json, CartItemVO.class);
    }

    @Override
    public void updateCheckStatus(Long skuId, Boolean checked) {
        Assert.notNull(skuId, "商品id不能为空!");
        BoundHashOperations<String, Object, Object> ops = getCartOps();
        String json = (String) ops.get(skuId.toString());
        CartItemVO itemVO = JSON.parseObject(json, CartItemVO.class);
        assert itemVO != null;
        itemVO.setCheck(checked);

        ops.put(skuId.toString(), JSON.toJSONString(itemVO));
    }


    /**
     * 创建并添加到redis
     *
     * @param ops   redis 操作符
     * @param skuId 商品id
     * @param count 商品数量
     */
    private void createAndAdd(BoundHashOperations<String, Object, Object> ops, Long skuId, int count) {
        // TODO 并发优化
        CartItemVO itemVO = new CartItemVO();
        // 远程查询商品信息
        R productR = productFeignService.info(skuId);
        if (productR.getCode() != 0) {
            throw new FeignRequestException("远程调用错误");
        }
        SkuInfoDTO skuInfo = productR.getData("skuInfo", new TypeReference<>() {
        });
        itemVO.setSkuId(skuInfo.getSkuId());
        itemVO.setTitle(skuInfo.getSkuTitle());
        itemVO.setImage(skuInfo.getSkuDefaultImg());
        itemVO.setPrice(skuInfo.getPrice());
        itemVO.setCount(count);
        itemVO.setCheck(true);

        // 远程查询商品销售属性信息
        R saleAttrP = productFeignService.getSkuSaleAttrValues(skuId);
        if (saleAttrP.getCode() != 0) {
            throw new FeignRequestException("远程调用错误");
        }
        List<String> saleAttrs = saleAttrP.getData("saleAttrs", new TypeReference<>() {
        });
        itemVO.setSkuAttrValues(saleAttrs);

        // 存入Redis
        ops.put(skuId.toString(), JSON.toJSONString(itemVO));
    }


    @Override
    public List<CartItemVO> getCartItems() {
        BoundHashOperations<String, Object, Object> ops = getCartOps();
        // Get items from redis
        List<Object> values = ops.values();
        if (!CollectionUtils.isEmpty(values)) {
            return values.stream().map(value -> {
                String json = (String) value;
                return JSON.parseObject(json, CartItemVO.class);
            }).collect(Collectors.toList());
        }
        return null;
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
