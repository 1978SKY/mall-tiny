package com.deep.seckill.model.params;

import lombok.Data;

/**
 * 进行秒杀参数
 *
 * @author Deep
 * @date 2022/5/2
 */
@Data
public class DoSeckillParam {
    /**
     * 秒杀活动id
     */
    private Long sessionId;
    /**
     * 秒杀商品id
     */
    private Long skuId;
    /**
     * 秒杀数量
     */
    private Integer count;
    /**
     * token 保证幂等性
     */
    private String token;
}
