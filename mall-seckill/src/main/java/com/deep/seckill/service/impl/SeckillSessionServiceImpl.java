package com.deep.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.common.utils.PageUtils;
import com.deep.common.utils.Query;
import com.deep.seckill.dao.SeckillSessionDao;
import com.deep.seckill.model.entity.SeckillSessionEntity;
import com.deep.seckill.service.SeckillSessionService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 每日秒杀
 *
 * @author Deep
 * @date 2022/4/16
 */
@Service("seckillSessionService")
public class SeckillSessionServiceImpl
        extends ServiceImpl<SeckillSessionDao, SeckillSessionEntity> implements SeckillSessionService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<SeckillSessionEntity> wrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (StringUtils.hasLength(key)) {
            wrapper.eq("id", key).or().like("name", key);
        }
        IPage<SeckillSessionEntity> iPage = this.page(new Query<SeckillSessionEntity>().getPage(params), wrapper);
        return new PageUtils(iPage);
    }
}
