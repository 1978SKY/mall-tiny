package com.deep.seckill.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deep.seckill.model.entity.SkuFullReductionEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品满减信息
 * 
 * @author deep
 * @email ${email}
 * @date 2022-01-13 17:28:12
 */
@Mapper
public interface SkuFullReductionDao extends BaseMapper<SkuFullReductionEntity> {
	
}
