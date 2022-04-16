package com.deep.order.model.vo;

import com.deep.order.model.dto.CartItemDTO;
import com.deep.order.model.dto.MemberAddressDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 确认订单
 *
 * @author Deep
 * @date 2022/4/5
 */
@Data
public class OrderConfirmVO {
    /**
     * 收货地址
     */
    private List<MemberAddressDTO> memberAddressDTOS;
    /**
     * 订单项
     */
    private List<CartItemDTO> items;
    /**
     * 是否有货
     */
    private Map<Long, Boolean> stocks;
    /**
     * 会员积分
     */
    private Integer integration;
    /**
     * 订单令牌（确保幂等性，防止用户重复提交）
     */
    private String orderToken;

    /**
     * 获取商品件数
     */
    public Integer getCount() {
        Integer count = 0;
        for (CartItemDTO item : items) {
            if(stocks.get(item.getSkuId())){
                count += item.getCount();
            }
        }
        return count;
    }

    /**
     * 商品总额
     */
    public BigDecimal getTotal() {
        BigDecimal totalNum = BigDecimal.ZERO;
        for (CartItemDTO item : items) {
            if (stocks.get(item.getSkuId())) {
                // 计算当前商品的总价格
                BigDecimal itemPrice = item.getPrice().multiply(new BigDecimal(item.getCount().toString()));
                // 再计算全部商品的总价格
                totalNum = totalNum.add(itemPrice);
            }
        }
        return totalNum;
    }

    /**
     * 应付总额
     */
    public BigDecimal getPayPrice() {
        return getTotal();
    }
}
