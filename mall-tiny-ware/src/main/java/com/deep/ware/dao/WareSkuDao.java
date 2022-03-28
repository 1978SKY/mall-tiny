package com.deep.ware.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deep.ware.model.entity.WareSkuEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品库存
 *
 * @author Deep
 * @date 2022/3/28
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {
}
