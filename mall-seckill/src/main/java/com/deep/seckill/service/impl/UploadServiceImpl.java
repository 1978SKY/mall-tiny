package com.deep.seckill.service.impl;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.deep.common.exception.FeignRequestException;
import com.deep.common.model.dto.SkuInfoDTO;
import com.deep.common.utils.R;
import com.deep.seckill.feign.CouponFeignService;
import com.deep.seckill.feign.ProductFeignService;
import com.deep.seckill.model.constant.SeckillConstant;
import com.deep.seckill.model.dto.SeckillSessionWithSkusDTO;
import com.deep.seckill.model.dto.SeckillSkuDTO;
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
    private final ProductFeignService productFeignService;

    public UploadServiceImpl(StringRedisTemplate redisTemplate, CouponFeignService couponFeignService,
        ProductFeignService productFeignService) {
        this.redisTemplate = redisTemplate;
        this.couponFeignService = couponFeignService;
        this.productFeignService = productFeignService;
    }

    @Override
    public boolean uploadSeckillSkus(int day) {
        R r = couponFeignService.getSeckillSessions(day);
        List<SeckillSessionWithSkusDTO> sessionWithSkusList = r.getData("data", new TypeReference<>() {});
        // 无需上架
        if (sessionWithSkusList.isEmpty()) {
            return false;
        }

        return saveSessions(sessionWithSkusList) && saveSkus(sessionWithSkusList);
    }

    @Override
    public List<SeckillSkuDTO> getSeckillSkus() {
        Set<String> keys = redisTemplate.keys(SeckillConstant.SKU_CACHE_PREFIX + "*");

        List<SeckillSkuDTO> skus = new ArrayList<>();

        HashOperations<String, String, String> ops = redisTemplate.opsForHash();
        assert keys != null;
        if (!keys.isEmpty()) {
            for (String key : keys) {
                Map<String, String> map = ops.entries(key);
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    SeckillSkuDTO skuDTO = JSON.parseObject(entry.getValue(), SeckillSkuDTO.class);
                    skus.add(skuDTO);
                }
            }
        }
        return skus;
    }

    /**
     * 缓存秒杀活动 key：时间 hashKey：sessionId value：秒杀活动
     * 
     * @param sessionWithSkusList 秒杀
     * @return true/false
     */
    private boolean saveSessions(List<SeckillSessionWithSkusDTO> sessionWithSkusList) {
        Assert.notEmpty(sessionWithSkusList, "秒杀活动不能为空!");

        HashOperations<String, String, String> ops = redisTemplate.opsForHash();

        for (SeckillSessionWithSkusDTO session : sessionWithSkusList) {
            long startTime = session.getStartTime().getTime();
            long endTime = session.getEndTime().getTime();
            String key = SeckillConstant.SESSION_CACHE_PREFIX + startTime + "-" + endTime;
            Boolean opsR = ops.putIfAbsent(key, session.getId().toString(), JSON.toJSONString(session));
            if (opsR) {
                redisTemplate.expire(key, Duration.ofMillis(endTime - startTime + 100));
            }
        }
        return true;
    }

    /**
     * 缓存秒杀商品（存储格式 k：活动id hashKey：商品id value：商品信息）
     * 
     * @param sessionWithSkusList 秒杀活动
     * @return true/false
     */
    private boolean saveSkus(List<SeckillSessionWithSkusDTO> sessionWithSkusList) {
        Assert.notEmpty(sessionWithSkusList, "秒杀商品不能为空!");
        for (SeckillSessionWithSkusDTO session : sessionWithSkusList) {
            String key = SeckillConstant.SKU_CACHE_PREFIX + session.getId();
            List<SeckillSkuDTO> skus = session.getSeckillSkus();
            if (skus == null || skus.isEmpty()) {
                continue;
            }
            List<Long> skuIds = skus.stream().map(SeckillSkuDTO::getSkuId).collect(Collectors.toList());
            R r = productFeignService.infos(skuIds);
            if (r.getCode() != 0) {
                throw new FeignRequestException("远程服务调用错误!");
            }
            List<SkuInfoDTO> skuInfos = r.getData("data", new TypeReference<>() {});
            if (skuInfos == null || skuInfos.isEmpty()) {
                continue;
            }
            // 映射为map方便组装
            Map<Long, SkuInfoDTO> skuInfoMap =
                skuInfos.stream().collect(Collectors.toMap(SkuInfoDTO::getSkuId, v -> v));
            // 数据过滤并组装
            Map<String, String> map =
                skus.stream().filter(item -> item.getSkuId() != null && skuInfoMap.containsKey(item.getSkuId()))
                    .collect(Collectors.toMap(k -> k.getSkuId().toString(), v -> {
                        v.setSkuInfoDTO(skuInfoMap.get(v.getSkuId()));
                        return JSON.toJSONString(v);
                    }));

            redisTemplate.opsForHash().putAll(key, map);

            long startTime = session.getStartTime().getTime();
            long endTime = session.getEndTime().getTime();
            redisTemplate.expire(key, Duration.ofMillis(endTime - startTime + 100));
        }
        return true;
    }

    /**
     * 检查是否在当前时间范围内
     * 
     * @param key redis key
     * @return true/false
     */
    private boolean checkTime(String key) {
        long now = System.currentTimeMillis();
        String replace = key.replace(SeckillConstant.SESSION_CACHE_PREFIX, "");
        long start = Long.parseLong(replace.split("-")[0]);
        long end = Long.parseLong(replace.split("-")[1]);
        return start <= now && end >= now;
    }
}
