package com.deep.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deep.product.model.entity.CategoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品三级分类
 *
 * @author Deep
 * @date 2022/3/13
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
    int logicDelete(@Param("ids") List<Long> ids);

    List<CategoryEntity> executeSelect(@Param("sql") String sql);
}