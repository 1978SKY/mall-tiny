package com.deep.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deep.auth.model.entity.MemberEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 *
 * @author Deep
 * @date 2022/4/1
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {

}