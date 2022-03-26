package com.deep.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deep.product.model.entity.SkuImagesEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * sku图片
 *
 * @author Deep
 * @date 2022/3/24
 */
@Mapper
public interface SkuImagesDao extends BaseMapper<SkuImagesEntity> {
}
