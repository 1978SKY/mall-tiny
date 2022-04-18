package com.deep.coupon.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deep.coupon.model.entity.HomeSubjectEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】
 * 
 * @author deep
 * @email ${email}
 * @date 2022-01-13 17:28:13
 */
@Mapper
public interface HomeSubjectDao extends BaseMapper<HomeSubjectEntity> {
	
}
