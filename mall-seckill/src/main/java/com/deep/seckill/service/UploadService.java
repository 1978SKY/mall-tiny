package com.deep.seckill.service;

import java.util.List;

import com.deep.seckill.model.dto.RedisSkuDto;
import com.deep.seckill.model.dto.SeckillSkuDTO;
import com.deep.seckill.model.vo.SessionLocalSkuVo;
import org.springframework.lang.NonNull;

/**
 * 上架服务
 *
 * @author Deep
 * @date 2022/4/18
 */
public interface UploadService {
    /**
     * 上架秒杀商品
     * 
     * @param day 最近天数
     * @return 上架结果
     */
    boolean uploadSeckillSkus(int day);

    /**
     * 获取所有秒杀商品
     * 
     * @return 秒杀商品
     */
    List<SessionLocalSkuVo> listSeckillSkus();

    /**
     * 获取秒杀商品详情
     * 
     * @param sessionId 活动id
     * @param skuId 商品详情
     * @return 商品详情
     */
    RedisSkuDto getSkuDetail(@NonNull Long sessionId, @NonNull Long skuId);
}
