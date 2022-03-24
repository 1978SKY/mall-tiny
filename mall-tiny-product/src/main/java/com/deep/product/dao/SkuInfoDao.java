package com.deep.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deep.product.model.entity.SkuInfoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * sku信息
 *
 * @author Deep
 * @date 2022/3/20
 */
@Mapper
public interface SkuInfoDao extends BaseMapper<SkuInfoEntity> {
}
