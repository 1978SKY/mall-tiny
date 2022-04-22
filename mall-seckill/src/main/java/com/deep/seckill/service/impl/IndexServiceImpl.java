package com.deep.seckill.service.impl;

import java.util.List;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.deep.seckill.feign.ProductFeignService;
import com.deep.seckill.model.dto.SeckillSkuDTO;
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
    public List<SeckillSkuDTO> getSeckillSessions() {
        return uploadService.getSeckillSkus();
    }

    @Override
    public SeckillSkuDTO getSeckillSkuDetail(@NonNull Long skuId) {
        Assert.notNull(skuId, "商品id不能为空!");

        return null;
    }
}
