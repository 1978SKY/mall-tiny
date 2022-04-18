package com.deep.coupon.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deep.coupon.model.entity.SeckillSkuRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 秒杀活动商品关联
 *
 * @author deep
 * @email ${email}
 * @date 2022-01-13 17:28:12
 */
@Mapper
public interface SeckillSkuRelationDao extends BaseMapper<SeckillSkuRelationEntity> {

    /**
     * 由sessionIds获取秒杀商品
     *
     * @param sessionIds 每日秒杀id集合
     * @return 所有秒杀商品
     */
    List<SeckillSkuRelationEntity> getBySessionIds(@Param("sessionIds") List<Long> sessionIds);
}
