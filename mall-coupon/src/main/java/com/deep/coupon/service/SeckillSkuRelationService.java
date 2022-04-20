package com.deep.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deep.common.utils.PageUtils;
import com.deep.coupon.model.entity.SeckillSkuRelationEntity;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Map;

/**
 * 每日秒杀&商品关联
 *
 * @author Deep
 * @date 2022/4/17
 */
public interface SeckillSkuRelationService extends IService<SeckillSkuRelationEntity> {

    /**
     * 查询秒杀活动商品
     *
     * @param params 查询参数
     * @return 秒杀活动商品
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 获取每日秒杀关联的商品(k:每日秒杀id，v:商品集合)
     *
     * @param sessionIds 秒杀id
     * @return 商品Hash集合()
     */
    Map<Long, List<SeckillSkuRelationEntity>> getBySessionIds(@NonNull List<Long> sessionIds);

    /**
     * 保存或修改秒杀关联的商品
     * 
     * @param seckillSkuRelation 秒杀活动商品关联
     * @return 成功/失败
     */
    Map<Boolean,String> saveOrUpdateDetail(@NonNull SeckillSkuRelationEntity seckillSkuRelation);
}
