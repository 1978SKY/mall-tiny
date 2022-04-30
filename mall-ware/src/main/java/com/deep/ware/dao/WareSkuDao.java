package com.deep.ware.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deep.ware.model.entity.WareSkuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品库存
 *
 * @author Deep
 * @date 2022/3/28
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {
    /**
     * 通过skuId集合获取商品库存
     */
    List<WareSkuEntity> getBySkuIds(@Param("skuIds") List<Long> skuIds);

    /**
     * 解锁库存
     *
     * @param skuId  商品id
     * @param wareId 仓库id
     * @param skuNum 解锁数量
     * @return 1:解锁成功
     */
    int unlockInventory(@Param("skuId") Long skuId, @Param("wareId") Long wareId, @Param("skuNum") Integer skuNum);
}
