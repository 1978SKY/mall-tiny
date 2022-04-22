package com.deep.seckill.service;

import java.util.List;

import org.springframework.lang.NonNull;

import com.deep.seckill.model.dto.SeckillSessionWithSkusDTO;
import com.deep.seckill.model.dto.SeckillSkuDTO;

/**
 * 秒杀首页
 * 
 * @author Deep
 * @date 2022/4/21
 */
public interface IndexService {

    /**
     * 查询秒杀商品
     * 
     * @param params 查询参数
     * @return 秒杀商品页
     */
    List<SeckillSkuDTO> getSeckillSessions();

    /**
     * 获取秒杀商品详情
     * 
     * @param skuId 商品id
     * @return 商品详情
     */
    SeckillSkuDTO getSeckillSkuDetail(@NonNull Long skuId);
}
