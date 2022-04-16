package com.deep.seckill.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deep.seckill.model.entity.SeckillSkuNoticeEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 秒杀商品通知订阅
 * 
 * @author deep
 * @email ${email}
 * @date 2022-01-13 17:28:12
 */
@Mapper
public interface SeckillSkuNoticeDao extends BaseMapper<SeckillSkuNoticeEntity> {
	
}
