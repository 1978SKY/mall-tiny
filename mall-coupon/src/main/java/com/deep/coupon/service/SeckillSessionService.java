package com.deep.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deep.common.utils.PageUtils;
import com.deep.coupon.model.entity.SeckillSessionEntity;
import com.deep.coupon.model.vo.SeckillSessionWithSkusVO;

import java.util.List;
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

    /**
     * 获取指定时间内的秒杀活动
     *
     * @param day 时间
     * @return 秒杀活动
     */
    List<SeckillSessionWithSkusVO> querySeckillSessions(int day);
}
