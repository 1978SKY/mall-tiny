package com.deep.ware.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deep.ware.model.entity.PurchaseDemandEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 采购需求
 *
 * @author Deep
 * @date 2022/3/28
 */
@Mapper
public interface PurchaseDemandDao extends BaseMapper<PurchaseDemandEntity> {

}
