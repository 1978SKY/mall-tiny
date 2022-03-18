package com.deep.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deep.product.model.entity.CategoryBrandRelationEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 品牌分类关联
 *
 * @author Deep
 * @date 2022/3/17
 */
@Mapper
public interface CategoryBrandRelationDao extends BaseMapper<CategoryBrandRelationEntity> {
}
