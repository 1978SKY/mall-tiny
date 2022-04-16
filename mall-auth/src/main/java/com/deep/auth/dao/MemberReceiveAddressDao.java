package com.deep.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deep.auth.model.entity.MemberReceiveAddressEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员收货地址
 *
 * @author Deep
 * @date 2022/4/5
 */
@Mapper
public interface MemberReceiveAddressDao extends BaseMapper<MemberReceiveAddressEntity> {

}
