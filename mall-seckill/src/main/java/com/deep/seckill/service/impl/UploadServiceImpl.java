package com.deep.seckill.service.impl;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.deep.common.exception.FeignRequestException;
import com.deep.common.model.dto.SkuInfoDTO;
import com.deep.common.utils.BeanUtils;
import com.deep.common.utils.R;
import com.deep.seckill.feign.CouponFeignService;
import com.deep.seckill.feign.ProductFeignService;
import com.deep.seckill.model.constant.SeckillConstant;
import com.deep.seckill.model.dto.RedisSessionDto;
import com.deep.seckill.model.dto.RedisSkuDto;
import com.deep.seckill.model.dto.SeckillSessionWithSkusDTO;
import com.deep.seckill.model.dto.SeckillSkuDTO;
import com.deep.seckill.model.vo.SessionLocalSkuVo;
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
        return saveSessions(sessionWithSkusList);
    }

    @Override
    public List<SessionLocalSkuVo> listSeckillSkus() {
        // 获取所有的秒杀活动key
        Set<String> keys = redisTemplate.keys(SeckillConstant.SESSION_CACHE_PREFIX + "*");
        if (keys == null) {
            return new ArrayList<>();
        }
        HashOperations<String, String, String> ops = redisTemplate.opsForHash();
        List<SessionLocalSkuVo> sessions = new ArrayList<>();
        // 过滤已经超时的key，未超时的key获取其对应的商品
        keys.stream().filter(this::checkTime).forEach(key -> {
            Map<String, String> map = ops.entries(key);
            // 获取所有的秒杀商品
            for (Map.Entry<String, String> entry : map.entrySet()) {
                RedisSessionDto sessionDto = JSON.parseObject(entry.getValue(), RedisSessionDto.class);
                SessionLocalSkuVo session = BeanUtils.transformFrom(sessionDto, SessionLocalSkuVo.class);
                if (sessionDto == null || sessionDto.getStatus() == null || session == null) {
                    continue;
                }
                // 秒杀商品id集合
                List<String> skuIds =
                    sessionDto.getSkuIds().stream().map(Object::toString).collect(Collectors.toList());
                // 获取所有的秒杀商品
                List<String> skusStr = ops.multiGet(SeckillConstant.SKU_CACHE_PREFIX + sessionDto.getId(), skuIds);
                List<SessionLocalSkuVo.LocalSkuVo> localSkuVos = skusStr.stream().map(skuStr -> {
                    RedisSkuDto skuDto = JSON.parseObject(skuStr, RedisSkuDto.class);
                    SessionLocalSkuVo.LocalSkuVo localSkuVo = new SessionLocalSkuVo.LocalSkuVo();
                    localSkuVo.setSkuId(skuDto.getSkuId());
                    localSkuVo.setSessionId(skuDto.getSessionId());
                    localSkuVo.setSeckillPrice(skuDto.getSeckillPrice());
                    localSkuVo.setSaleCount(skuDto.getSeckillCount());
                    RedisSkuDto.SkuInfoDto infoDto = skuDto.getInfo();
                    localSkuVo.setPrice(infoDto.getPrice());
                    localSkuVo.setSkuDefaultImg(infoDto.getSkuDefaultImg());
                    localSkuVo.setSkuName(infoDto.getSkuName());
                    localSkuVo.setSkuTitle(infoDto.getSkuTitle());
                    return localSkuVo;
                }).collect(Collectors.toList());
                session.setLocalVos(localSkuVos);
                sessions.add(session);
            }
        });
        return sessions;
    }

    @Override
    public RedisSkuDto getSkuDetail(@NonNull Long sessionId, @NonNull Long skuId) {
        Assert.notNull(sessionId, "活动id不能为空!");
        Assert.notNull(skuId, "商品id不能为空!");
        HashOperations<String, String, String> ops = redisTemplate.opsForHash();
        String skuStr = ops.get(SeckillConstant.SKU_CACHE_PREFIX + sessionId, skuId.toString());
        if (StringUtils.hasLength(skuStr)) {
            return JSON.parseObject(skuStr, RedisSkuDto.class);
        }
        return null;
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
            List<SeckillSkuDTO> skus = session.getSeckillSkus();
            if (skus == null || skus.isEmpty()) {
                continue;
            }
            List<Long> skuIds = skus.stream().map(SeckillSkuDTO::getSkuId).collect(Collectors.toList());
            RedisSessionDto sessionDto = BeanUtils.transformFrom(session, RedisSessionDto.class);
            assert sessionDto != null;
            sessionDto.setSkuIds(skuIds);
            // 缓存session k：时间 hashKey：sessionId v：RedisSessionDto
            Boolean opsR = ops.putIfAbsent(key, session.getId().toString(), JSON.toJSONString(sessionDto));
            if (opsR) {
                redisTemplate.expire(key, Duration.ofMillis(endTime - startTime));
                // 缓存秒杀商品
                saveSkus(session, skuIds);
            }

        }
        return true;
    }

    /**
     * 缓存秒杀商品
     *
     * @param session 秒杀活动
     * @param skuIds 商品id集合
     */
    private void saveSkus(SeckillSessionWithSkusDTO session, List<Long> skuIds) {
        // save skus
        String key = SeckillConstant.SKU_CACHE_PREFIX + session.getId();
        List<SeckillSkuDTO> skus = session.getSeckillSkus();
        if (!skus.isEmpty() && !skuIds.isEmpty()) {
            List<RedisSkuDto> redisSkuDtos = BeanUtils.transformFromInBatch(skus, RedisSkuDto.class);
            R r = productFeignService.infos(skuIds);
            if (r.getCode() != 0) {
                throw new FeignRequestException("远程服务调用错误!");
            }
            List<SkuInfoDTO> skuInfos = r.getData("data", new TypeReference<>() {});
            if (skuInfos != null && !skuInfos.isEmpty()) {
                // 映射为map方便组装
                Map<Long, SkuInfoDTO> skuInfoMap =
                    skuInfos.stream().collect(Collectors.toMap(SkuInfoDTO::getSkuId, v -> v));
                // 数据过滤并组装
                Map<String,
                    String> redisMap = redisSkuDtos.stream()
                        .filter(item -> item.getSkuId() != null && skuInfoMap.containsKey(item.getSkuId()))
                        .collect(Collectors.toMap(k -> k.getSkuId().toString(), v -> {
                            v.setSessionId(session.getId());
                            v.setName(session.getName());
                            v.setStartTime(session.getStartTime());
                            v.setEndTime(session.getEndTime());
                            RedisSkuDto.SkuInfoDto skuInfoDto =
                                BeanUtils.transformFrom(skuInfoMap.get(v.getSkuId()), RedisSkuDto.SkuInfoDto.class);
                            v.setInfo(skuInfoDto);
                            return JSON.toJSONString(v);
                        }));
                redisTemplate.opsForHash().putAll(key, redisMap);
                redisTemplate.expire(key,
                    Duration.ofMillis(session.getEndTime().getTime() - session.getStartTime().getTime()));
            }
        }
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
