package com.deep.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.common.utils.PageUtils;
import com.deep.common.utils.Query;
import com.deep.seckill.dao.SeckillSkuRelationDao;
import com.deep.seckill.model.entity.SeckillSkuRelationEntity;
import com.deep.seckill.service.SeckillSkuRelationService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 每日秒杀&商品关联
 *
 * @author Deep
 * @date 2022/4/17
 */
@Service("seckillSkuRelationService")
public class SeckillSkuRelationServiceImpl
        extends ServiceImpl<SeckillSkuRelationDao, SeckillSkuRelationEntity> implements SeckillSkuRelationService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<SeckillSkuRelationEntity> wrapper = new QueryWrapper<>();
        // 1、封装活动场次id，关联
        String promotionSessionId = (String) params.get("promotionSessionId");
        if (StringUtils.hasLength(promotionSessionId)) {
            wrapper.eq("promotion_session_id", promotionSessionId);
        }
        // 2、获取key
        String key = (String) params.get("key");
        if (StringUtils.hasLength(key)) {
            wrapper.eq("id", key);
        }
        IPage<SeckillSkuRelationEntity> page = this.page(
                new Query<SeckillSkuRelationEntity>().getPage(params),
                wrapper
        );
        return new PageUtils(page);
    }
}
