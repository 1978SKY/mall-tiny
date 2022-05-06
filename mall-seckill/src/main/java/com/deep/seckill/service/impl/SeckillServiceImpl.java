package com.deep.seckill.service.impl;

import com.deep.common.model.dto.SeckillOrderDto;
import com.deep.common.utils.DateUtil;
import com.deep.common.utils.SnowflakeIdWorker;
import com.deep.seckill.interceptor.LoginInterceptor;
import com.deep.seckill.model.constant.SeckillConstant;
import com.deep.seckill.model.dto.RedisSkuDto;
import com.deep.seckill.model.enume.DeductionEnums;
import com.deep.seckill.model.params.DoSeckillParam;
import com.deep.seckill.model.vo.SessionLocalSkuVo;
import com.deep.seckill.service.SeckillService;
import com.deep.seckill.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 秒杀首页
 *
 * @author Deep
 * @date 2022/4/21
 */
@Slf4j
@Service("indexService")
public class SeckillServiceImpl implements SeckillService {
    private final StringRedisTemplate redisTemplate;
    private final RabbitTemplate rabbitTemplate;
    private final UploadService uploadService;

    public SeckillServiceImpl(StringRedisTemplate redisTemplate, RabbitTemplate rabbitTemplate, UploadService uploadService) {
        this.redisTemplate = redisTemplate;
        this.rabbitTemplate = rabbitTemplate;
        this.uploadService = uploadService;
    }

    @Override
    public List<SessionLocalSkuVo.LocalSkuVo> getSeckillSessions() {
        List<SessionLocalSkuVo> sessions = uploadService.listSeckillSkus();
        ArrayList<SessionLocalSkuVo.LocalSkuVo> skuVos = new ArrayList<>();
        sessions.forEach(item -> skuVos.addAll(item.getLocalVos()));
        return skuVos;
    }

    @Override
    public RedisSkuDto getSeckillSkuDetail(@NonNull Long sessionId, @NonNull Long skuId) {
        Assert.notNull(sessionId, "活动id不能为空!");
        Assert.notNull(skuId, "商品id不能为空!");
        RedisSkuDto redisSkuDto = uploadService.getSkuDetail(sessionId, skuId);
        // TODO 组合相关属性

        return redisSkuDto;
    }

    @Override
    public Map<Boolean, String> doSeckill(DoSeckillParam param) {
        Long memberId = LoginInterceptor.LOGIN_USER_THREAD_LOCAL.get().getId();
        // 结果
        Map<Boolean, String> resMap = new HashMap<>(1);

        // 校验是否参与秒杀活动
        RedisSkuDto redisSkuDto = uploadService.getSkuDetail(param.getSessionId(), param.getSkuId());
        if (redisSkuDto == null) {
            resMap.put(false, "该商品没有参与秒杀活动!");
            return resMap;
        }
        // 校验秒杀时间
        if (!DateUtil.checkTime(redisSkuDto.getStartTime().getTime(), redisSkuDto.getEndTime().getTime())) {
            resMap.put(false, "商品不在秒杀范围时间内!");
            return resMap;
        }
        // 校验token(幂等性校验)
        if (!checkToken(param, redisSkuDto.getEndTime().getTime(), memberId)) {
            resMap.put(false, "当前用户已参与过该商品秒杀!");
            return resMap;
        }
        // 扣除库存(Redis中的库存) 后续生成订单后，由MQ通知库存系统进行库存扣除
        String deductionMsg = deductionInventory(param.getSkuId(), param.getCount());
        if (!DeductionEnums.DEDUCTION_SUCCESS.getMsg().equals(deductionMsg)) {
            resMap.put(false, deductionMsg);
            return resMap;
        }
        // 创建订单
        String orderSn = createSimpleOrder(redisSkuDto, param.getCount(), memberId);
        if (!StringUtils.hasLength(orderSn)) {
            resMap.put(false, "订单创建失败!");
        }
        resMap.put(true, orderSn);
        return resMap;
    }

    /**
     * 创建订单并通知MQ
     * @param redisSkuDto 商品信息
     * @param count 购买数量
     * @param memberId 会员id
     * @return 订单号
     */
    private String createSimpleOrder(RedisSkuDto redisSkuDto, Integer count, Long memberId) {
        Assert.notNull(redisSkuDto, "商品信息不能为空!");
        Assert.notNull(count, "购买数量不能为空!");
        Assert.notNull(memberId, "会员id不能为空!");
        // 简单创建订单
        long idWorker = new SnowflakeIdWorker(0, 0).nextId();
        String orderSn = String.valueOf(idWorker);
        SeckillOrderDto orderDto = new SeckillOrderDto(orderSn,
                redisSkuDto.getSessionId(),
                redisSkuDto.getSkuId(),
                redisSkuDto.getSeckillPrice(),
                count,
                memberId);
        // 通知MQ
        rabbitTemplate.convertAndSend("order-event-exchange", "order.seckill.order", orderDto);
        return orderSn;
    }


    /**
     * 校验token(幂等性校验)
     *
     * @param param   秒杀参数
     * @param endTime 结束时间
     * @return 校验结果
     */
    private boolean checkToken(DoSeckillParam param, long endTime, Long memberId) {
        String userUniqueKey = SeckillConstant.USER_SECKILL_TOKEN + param.getSessionId() + "-"
                + param.getSkuId() + "-"
                + memberId;
        long ttl = endTime - System.currentTimeMillis();
        Boolean ifAbsent = redisTemplate.opsForValue()
                .setIfAbsent(userUniqueKey, param.getCount().toString(), ttl, TimeUnit.MILLISECONDS);
        return ifAbsent != null && ifAbsent;
    }

    /**
     * 扣减库存(Redis中的库存)
     *
     * @param skuId 商品id
     * @param count 扣减数量
     * @return 扣减结果描述
     */
    private String deductionInventory(Long skuId, Integer count) {
        Assert.notNull(skuId, "商品id不能为空!");
        Assert.notNull(count, "扣减数量能为空!");

        String key = SeckillConstant.COUNT_CACHE_PREFIX + skuId;
        // Lua脚本
        String script = "local key = KEYS[1];\n" +
                "local count = tonumber(ARGV[1]);\n" +
                "local last = tonumber(redis.call('get',key));\n" +
                "if(last<=0) then return 0\n" +
                "elseif(count > last) then return -1\n" +
                "else\n" +
                "   redis.call('decrby',key,count)\n" +
                "   return 1\n" +
                "end";
        // 执行Lua脚本
        Long res = redisTemplate.execute(new DefaultRedisScript<>(script, Long.class),
                Collections.singletonList(key),
                count.toString());

        assert res != null;
        return DeductionEnums.getMsg(res);
    }
}
