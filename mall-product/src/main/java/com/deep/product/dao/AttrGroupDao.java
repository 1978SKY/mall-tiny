package com.deep.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deep.product.model.entity.AttrGroupEntity;
import com.deep.product.model.vo.AttrVO;
import com.deep.product.model.vo.SpuItemAttrGroupVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Deep
 * @date 2022/3/17
 */
@Mapper
public interface AttrGroupDao extends BaseMapper<AttrGroupEntity> {
    List<SpuItemAttrGroupVO> getAttrGroupWithAttrs(@Param("spuId") Long spuId,@Param("catId") Long catId);
}
