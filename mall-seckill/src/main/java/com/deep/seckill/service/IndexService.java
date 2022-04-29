package com.deep.seckill.service;

import java.util.List;

import com.deep.seckill.model.dto.RedisSkuDto;
import com.deep.seckill.model.vo.SessionLocalSkuVo;
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
    List<SessionLocalSkuVo.LocalSkuVo> getSeckillSessions();

    /**
     * 获取秒杀商品详情
     * @param sessionId 活动id
     * @param skuId 商品详情
     * @return 商品详情
     */
    RedisSkuDto getSeckillSkuDetail(@NonNull Long sessionId, @NonNull Long skuId);
}
