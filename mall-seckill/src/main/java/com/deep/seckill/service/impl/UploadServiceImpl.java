package com.deep.seckill.service.impl;

import java.time.Duration;
import java.util.List;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.alibaba.fastjson.TypeReference;
import com.deep.common.utils.R;
import com.deep.seckill.feign.CouponFeignService;
import com.deep.seckill.model.constant.SeckillConstant;
import com.deep.seckill.model.dto.SeckillSessionWithSkusDTO;
import com.deep.seckill.service.UploadService;

/**
 * 上架服务
 *
 * @author Deep
 * @date 2022/4/18
 */
@Service("uploadService")
public class UploadServiceImpl implements UploadService {

    private final StringRedisTemplate redisTemplate;
    private final CouponFeignService couponFeignService;

    public UploadServiceImpl(StringRedisTemplate redisTemplate, CouponFeignService couponFeignService) {
        this.redisTemplate = redisTemplate;
        this.couponFeignService = couponFeignService;
    }

    @Override
    public boolean uploadSeckillSkus(int day) {
        R r = couponFeignService.getSeckillSessions(day);
        List<SeckillSessionWithSkusDTO> sessionWithSkusList = r.getData("data", new TypeReference<>() {});
        // 无需上架
        if (sessionWithSkusList.isEmpty()) {
            return false;
        }
        // 缓存秒杀活动
        return saveSessions(sessionWithSkusList);
    }

    private boolean saveSessions(List<SeckillSessionWithSkusDTO> sessionWithSkusList) {
        Assert.notEmpty(sessionWithSkusList, "秒杀活动不能为空!");
        for (SeckillSessionWithSkusDTO session : sessionWithSkusList) {

            long startTime = session.getStartTime().getTime();
            long endTime = session.getEndTime().getTime();
            String key = SeckillConstant.SESSION_CACHE_PREFIX + startTime + "-" + endTime;

            Boolean hasKey = redisTemplate.hasKey(key);
            if (hasKey != null && !hasKey) {
                redisTemplate.opsForHash().put(key, session.getId().toString(), JSON.toJSONString(session));
                redisTemplate.expire(key, Duration.ofMillis(endTime - startTime + 100));
            }
        }
        return true;
    }
}
