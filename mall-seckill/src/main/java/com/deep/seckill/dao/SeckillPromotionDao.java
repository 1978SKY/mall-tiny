package com.deep.seckill.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deep.seckill.model.entity.SeckillPromotionEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 秒杀活动
 * 
 * @author deep
 * @email ${email}
 * @date 2022-01-13 17:28:13
 */
@Mapper
public interface SeckillPromotionDao extends BaseMapper<SeckillPromotionEntity> {
	
}
