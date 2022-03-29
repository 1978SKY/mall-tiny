package com.deep.ware.service.Impl;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        String wareId = (String) params.get("wareId");
        if (StringUtils.hasLength(wareId)) {
            wrapper.eq("ware_id", wareId);
        }
        String skuId = (String) params.get("skuId");
        if (StringUtils.hasLength(skuId)) {
            wrapper.eq("sku_id", skuId);
        }
        IPage<WareSkuEntity> page = this.page(new Query<WareSkuEntity>().getPage(params), wrapper);

        return new PageUtils(page);
    }

    @Override
    public Map<Long, Boolean> skuIdsHasStock(List<Long> skuIds) {
        Assert.notEmpty(skuIds, "商品id集合不能为空!");
        HashMap<Long, Boolean> stockMap = new HashMap<>();
        List<WareSkuEntity> wareSkuEntities = wareSkuDao.getBySkuIds(skuIds);
        wareSkuEntities.forEach(item -> {
            Long skuId = item.getSkuId();
            if (!stockMap.containsKey(skuId) || !stockMap.get(skuId)) {
                boolean stock = item.getStock() - item.getStockLocked() > 0;
                stockMap.put(skuId, stock);
            }
        });
        return stockMap;
    }

    @Override
    public void finishPurchase(List<PurchaseDemandEntity> demandEntities) {
        Assert.notEmpty(demandEntities, "采购需求集合不能为空!");

        List<WareSkuEntity> collect = demandEntities.stream()
                .filter(item -> item.getStatus() < WareConstant.PurchaseDemandStatusEnum.FINISH.getCode()
                        || item.getStatus() == WareConstant.PurchaseDemandStatusEnum.HASERROR.getCode())
                .map(item -> {
                    WareSkuEntity wareSkuEntity = this.getOne(
                            new QueryWrapper<WareSkuEntity>()
                                    .eq("sku_id", item.getSkuId())
                                    .eq("ware_id", item.getWareId()));
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
}
