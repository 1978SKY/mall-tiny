package com.deep.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.common.utils.PageUtils;
import com.deep.common.utils.Query;
import com.deep.seckill.dao.SeckillPromotionDao;
import com.deep.seckill.model.entity.SeckillPromotionEntity;
import com.deep.seckill.service.SeckillPromotionService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 秒杀活动
 *
 * @author Deep
 * @date 2022/4/16
 */
@Service("seckillPromotionService")
public class SeckillPromotionServiceImpl
        extends ServiceImpl<SeckillPromotionDao, SeckillPromotionEntity> implements SeckillPromotionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<SeckillPromotionEntity> wrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (StringUtils.hasLength(key)) {
            wrapper.eq("id", key).or().eq("title", key);
        }
        IPage<SeckillPromotionEntity> page = this.page(
                new Query<SeckillPromotionEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }

}
