package com.deep.ware.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deep.ware.model.entity.WareInfoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 仓库信息
 *
 * @author Deep
 * @date 2022/3/28
 */
@Mapper
public interface WareInfoDao extends BaseMapper<WareInfoEntity> {
}
