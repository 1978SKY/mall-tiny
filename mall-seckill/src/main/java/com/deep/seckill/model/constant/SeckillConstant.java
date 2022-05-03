package com.deep.seckill.model.constant;

/**
 * 秒杀常量
 *
 * @author Deep
 * @date 2022/4/18
 */
public class SeckillConstant {

    /**
     * 秒杀商品上架功能的锁
     */
    public static final String UPLOAD_LOCK = "seckill:upload:lock";

    /**
     * 持有锁的时间
     */
    public static final Integer LOCK_TIME = 10;

    /**
     * 秒杀活动在Redis中前缀
     */
    public static final String SESSION_CACHE_PREFIX = "seckill:session:";

    /**
     * 秒杀商品在Redis中缓存前缀
     */
    public static final String SKU_CACHE_PREFIX = "seckill:sku:";

    /**
     * 秒杀商品数量在Redis中缓存前缀
     */
    public static final String COUNT_CACHE_PREFIX = "seckill:count:";

    /**
     * 用户秒杀token
     */
    public static final String USER_SECKILL_TOKEN = "seckill:token:";

}
