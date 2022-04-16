package com.deep.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deep.common.utils.PageUtils;
import com.deep.seckill.model.entity.SeckillSessionEntity;

import java.util.Map;

/**
 * 每日秒杀
 *
 * @author Deep
 * @date 2022/4/16
 */
public interface SeckillSessionService extends IService<SeckillSessionEntity> {
    /**
     * 获取每日秒杀活动
     *
     * @param params 查询参数
     * @return 每日秒杀
     */
    PageUtils queryPage(Map<String, Object> params);
}
