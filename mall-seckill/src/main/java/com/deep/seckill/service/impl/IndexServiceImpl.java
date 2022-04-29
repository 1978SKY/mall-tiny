package com.deep.seckill.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.deep.seckill.model.dto.RedisSkuDto;
import com.deep.seckill.model.vo.SessionLocalSkuVo;
import com.deep.seckill.service.IndexService;
import com.deep.seckill.service.UploadService;

/**
 * 秒杀首页
 * 
 * @author Deep
 * @date 2022/4/21
 */
@Service("indexService")
public class IndexServiceImpl implements IndexService {
    private final UploadService uploadService;

    public IndexServiceImpl(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @Override
    public List<SessionLocalSkuVo.LocalSkuVo> getSeckillSessions() {
        List<SessionLocalSkuVo> sessions = uploadService.listSeckillSkus();
        ArrayList<SessionLocalSkuVo.LocalSkuVo> skuVos = new ArrayList<>();
        sessions.forEach(item -> {
            skuVos.addAll(item.getLocalVos());
        });
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
}
