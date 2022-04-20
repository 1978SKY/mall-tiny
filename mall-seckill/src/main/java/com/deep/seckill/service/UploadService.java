package com.deep.seckill.service;

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
}
