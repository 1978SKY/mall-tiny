package com.deep.ware.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deep.ware.model.entity.WareOrderTaskEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 库存工作单
 *
 * @author Deep
 * @date 2022/3/28
 */
@Mapper
public interface WareOrderTaskDao extends BaseMapper<WareOrderTaskEntity> {
}
