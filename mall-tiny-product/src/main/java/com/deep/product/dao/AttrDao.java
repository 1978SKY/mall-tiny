package com.deep.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deep.product.model.entity.AttrEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品属性规格
 *
 * @author Deep
 * @date 2022/3/18
 */
@Mapper
public interface AttrDao extends BaseMapper<AttrEntity> {
}
