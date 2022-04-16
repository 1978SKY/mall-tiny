package com.deep.seckill.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deep.seckill.model.entity.SeckillSkuRelationEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 秒杀活动商品关联
 * 
 * @author deep
 * @email ${email}
 * @date 2022-01-13 17:28:12
 */
@Mapper
public interface SeckillSkuRelationDao extends BaseMapper<SeckillSkuRelationEntity> {
	
}
