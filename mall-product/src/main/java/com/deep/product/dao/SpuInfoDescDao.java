package com.deep.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deep.product.model.entity.SpuInfoDescEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * spu信息介绍
 *
 * @author Deep
 * @date 2022/3/24
 */
@Mapper
public interface SpuInfoDescDao extends BaseMapper<SpuInfoDescEntity> {
    
}
