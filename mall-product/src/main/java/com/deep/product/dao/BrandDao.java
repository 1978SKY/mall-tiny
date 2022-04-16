package com.deep.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deep.product.model.entity.BrandEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 品牌管理
 *
 * @author Deep
 * @date 2022/3/16
 */
@Mapper
public interface BrandDao extends BaseMapper<BrandEntity> {
}
