package com.deep.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deep.common.utils.PageUtils;
import com.deep.ware.model.entity.PurchaseDemandEntity;
import com.deep.ware.model.entity.WareSkuEntity;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author Deep
 * @date 2022/3/28
 */
public interface WareSkuService extends IService<WareSkuEntity> {
    /**
     * 获取商品库存列表
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 判断是否有商品库存
     */
    Map<Long, Boolean> skuIdsHasStock(@NonNull List<Long> skuIds);

    /**
     * 完成采购
     */
    void finishPurchase(@NonNull List<PurchaseDemandEntity> demandEntities);

    /**
     * 检查并锁定库存
     * 
     * @param skuId 商品id
     * @param count 数量
     * @return true/false
     */
    boolean lockInventory(@NonNull Long skuId, @NonNull Integer count);

    /**
     * 批量锁定库存
     * 
     * @param stockMap k: 商品id v:商品数量
     * @return true/false
     */
    boolean lockInventory(Map<Long, Integer> stockMap);
}
