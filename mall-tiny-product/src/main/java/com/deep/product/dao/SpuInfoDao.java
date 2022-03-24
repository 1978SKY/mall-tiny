package com.deep.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deep.product.model.entity.SpuInfoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * spu信息
 *
 * @author Deep
 * @date 2022/3/20
 */
@Mapper
public interface SpuInfoDao extends BaseMapper<SpuInfoEntity> {
}
