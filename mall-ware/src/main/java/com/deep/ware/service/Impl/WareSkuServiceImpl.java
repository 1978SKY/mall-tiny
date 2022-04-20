package com.deep.ware.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.common.utils.PageUtils;
import com.deep.common.utils.Query;
import com.deep.ware.dao.WareSkuDao;
import com.deep.ware.model.constant.WareConstant;
import com.deep.ware.model.entity.PurchaseDemandEntity;
import com.deep.ware.model.entity.WareSkuEntity;
import com.deep.ware.service.WareSkuService;

import lombok.extern.slf4j.Slf4j;

/**
 * 商品库存
 *
 * @author Deep
 * @date 2022/3/28
 */
@Slf4j
@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {
    @Autowired
    private WareSkuDao wareSkuDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<WareSkuEntity> wrapper = new QueryWrapper<>();
        String wareId = (String)params.get("wareId");
        if (StringUtils.hasLength(wareId)) {
            wrapper.eq("ware_id", wareId);
        }
        String skuId = (String)params.get("skuId");
        if (StringUtils.hasLength(skuId)) {
            wrapper.eq("sku_id", skuId);
        }
        IPage<WareSkuEntity> page = this.page(new Query<WareSkuEntity>().getPage(params), wrapper);

        return new PageUtils(page);
    }

    @Override
    public Map<Long, Boolean> skuIdsHasStock(@NonNull List<Long> skuIds) {
        Assert.notEmpty(skuIds, "商品id集合不能为空!");
        List<WareSkuEntity> wareSkuEntities = wareSkuDao.getBySkuIds(skuIds);

        HashMap<Long, Boolean> stockMap = new HashMap<>();
        for (WareSkuEntity wareSkuEntity : wareSkuEntities) {
            boolean hasStock = wareSkuEntity.getStock() - wareSkuEntity.getStockLocked() > 0;
            if (stockMap.containsKey(wareSkuEntity.getSkuId())) {
                hasStock = stockMap.get(wareSkuEntity.getSkuId()) || hasStock;
            }
            stockMap.put(wareSkuEntity.getSkuId(), hasStock);
        }

        for (Long skuId : skuIds) {
            if (!stockMap.containsKey(skuId)) {
                stockMap.put(skuId, false);
            }
        }
        return stockMap;
    }

    @Override
    public void finishPurchase(@NonNull List<PurchaseDemandEntity> demandEntities) {
        Assert.notEmpty(demandEntities, "采购需求集合不能为空!");

        List<WareSkuEntity> collect = demandEntities.stream()
            .filter(item -> item.getStatus() < WareConstant.PurchaseDemandStatusEnum.FINISH.getCode()
                || item.getStatus() == WareConstant.PurchaseDemandStatusEnum.HASERROR.getCode())
            .map(item -> {
                WareSkuEntity wareSkuEntity = this.getOne(
                    new QueryWrapper<WareSkuEntity>().eq("sku_id", item.getSkuId()).eq("ware_id", item.getWareId()));
                if (wareSkuEntity == null) {
                    // create new wareSkuEntity
                    wareSkuEntity = new WareSkuEntity();
                    wareSkuEntity.setSkuId(item.getSkuId());
                    wareSkuEntity.setWareId(item.getWareId());
                    wareSkuEntity.setStock(item.getSkuNum());
                } else {
                    // update stock
                    wareSkuEntity.setStock(wareSkuEntity.getStock() + item.getSkuNum());
                }
                return wareSkuEntity;
            }).collect(Collectors.toList());

        this.saveOrUpdateBatch(collect);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<Boolean, String> lockInventory(@NonNull Long skuId, @NonNull Integer count) {
        Assert.notNull(skuId, "商品id不能为空!");
        Assert.isTrue(count > 0, "商品数量必须为正数!");

        QueryWrapper<WareSkuEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("sku_id", skuId);
        List<WareSkuEntity> skus = list(wrapper);
        // check
        Map<Boolean, String> map = checkStock(count, skus);
        if (map != null) {
            return map;
        }
        map = new HashMap<>(1);
        // 锁定库存
        // TODO Redisson 锁（分布式锁，防止多扣库存）
        wrapper.clear();
        // 依次从扣除仓库库存，直到count为0之止
        for (int i = 0; i < skus.size() && count > 0; i++) {
            WareSkuEntity sku = skus.get(i);
            // 每个仓库剩余库存
            int subRemainStock = sku.getStock() - sku.getStockLocked();
            if (subRemainStock >= count) {
                sku.setStockLocked(sku.getStockLocked() + count);
            } else {
                sku.setStockLocked(sku.getStockLocked() + subRemainStock);
            }
            count -= subRemainStock;
        }
        // 更新库存
        updateBatchById(skus);
        map.put(true, null);
        return map;
    }

    /**
     * 检查库存
     * 
     * @param count 扣减库存数量
     * @param skus 商品集合
     * @return 检查结果
     */
    private Map<Boolean, String> checkStock(Integer count, List<WareSkuEntity> skus) {
        HashMap<Boolean, String> map = new HashMap<>(1);
        if (skus.isEmpty()) {
            map.put(false, "仓库没有该商品！");
            return map;
        }
        int remainStock = 0;
        for (WareSkuEntity sku : skus) {
            remainStock += sku.getStock() - sku.getStockLocked();
        }
        if (remainStock < count) {
            map.put(false, "仓库货存不足！");
            return map;
        }
        return null;
    }
}
