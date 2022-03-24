package com.deep.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deep.product.model.entity.AttrAttrgroupRelationEntity;
import com.deep.product.model.params.AttrGroupRelationParam;
import com.deep.product.model.vo.AttrVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性&属性分组关联
 *
 * @author Deep
 * @date 2022/3/18
 */
@Mapper
public interface AttrAttrgroupRelationDao extends BaseMapper<AttrAttrgroupRelationEntity> {
    /**
     * 获取属性分组名
     */
    String getGroupNameByAttrId(@Param("attrId") Long attrId);

    /**
     * 获取分组id下的所有属性
     */
    List<AttrVO> getAttrByGroupId(@Param("groupId") Long groupId);

    /**
     * 移除关联关系
     */
    void deleteRelations(@Param("params") List<AttrGroupRelationParam> params);
}
