package com.deep.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.common.utils.PageUtils;
import com.deep.common.utils.Query;
import com.deep.coupon.dao.SeckillSkuRelationDao;
import com.deep.coupon.model.entity.SeckillSkuRelationEntity;
import com.deep.coupon.service.SeckillSkuRelationService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 每日秒杀&商品关联
 *
 * @author Deep
 * @date 2022/4/17
 */
@Service("seckillSkuRelationService")
public class SeckillSkuRelationServiceImpl extends ServiceImpl<SeckillSkuRelationDao, SeckillSkuRelationEntity>
    implements SeckillSkuRelationService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<SeckillSkuRelationEntity> wrapper = new QueryWrapper<>();
        // 1、封装活动场次id，关联
        String promotionSessionId = (String)params.get("promotionSessionId");
        if (StringUtils.hasLength(promotionSessionId)) {
            wrapper.eq("promotion_session_id", promotionSessionId);
        }
        // 2、获取key
        String key = (String)params.get("key");
        if (StringUtils.hasLength(key)) {
            wrapper.eq("id", key);
        }
        IPage<SeckillSkuRelationEntity> page =
            this.page(new Query<SeckillSkuRelationEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }

    @Override
    public Map<Long, List<SeckillSkuRelationEntity>> getBySessionIds(@NonNull List<Long> sessionIds) {
        Assert.notEmpty(sessionIds, "秒杀场次id不能为空!");
        // map初始化
        HashMap<Long, List<SeckillSkuRelationEntity>> map = new HashMap<>(sessionIds.size());
        sessionIds.forEach(item -> map.put(item, null));
        // 装配skus
        List<SeckillSkuRelationEntity> skus = baseMapper.getBySessionIds(sessionIds);
        skus.forEach(item -> {
            List<SeckillSkuRelationEntity> subSkus = map.get(item.getPromotionSessionId());
            if (subSkus == null) {
                subSkus = new ArrayList<>();
            }
            subSkus.add(item);
            map.put(item.getPromotionSessionId(), subSkus);
        });
        return map;
    }
}
