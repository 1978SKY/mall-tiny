package com.deep.ware.service.Impl;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import com.deep.common.exception.StockException;
import com.deep.common.model.dto.OrderTaskDetailDto;
import com.deep.common.utils.BeanUtils;
import com.deep.ware.model.StockEnum;
import com.deep.ware.model.entity.WareOrderTaskDetailEntity;
import com.deep.ware.service.WareOrderTaskDetailService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    @Autowired
    private WareOrderTaskDetailService orderTaskDetailService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ThreadPoolExecutor executor;

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
    public boolean lockInventory(@NonNull Long skuId, @NonNull Integer count) {
        Assert.notNull(skuId, "商品id不能为空!");
        Assert.isTrue(count > 0, "商品数量必须为正数!");
        Map<Long, Integer> map = new HashMap<>(1);
        map.put(skuId, count);

        return lockInventory(null, map);

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean lockInventory(String orderSn, Map<Long, Integer> stockMap) {
        List<WareSkuEntity> wareSkuList = new ArrayList<>();
        List<WareOrderTaskDetailEntity> orderTaskDetailList = new ArrayList<>();

        QueryWrapper<WareSkuEntity> wrapper = new QueryWrapper<>();
        for (Map.Entry<Long, Integer> entry : stockMap.entrySet()) {
            wrapper.clear();
            Long skuId = entry.getKey();
            Integer count = entry.getValue();
            wrapper.eq("sku_id", skuId).last(" AND stock - stock_locked >= " + count + " LIMIT 1");
            WareSkuEntity wareSku = getOne(wrapper);
            if (wareSku == null) {
                // 仓库中没有该商品或库存不足
                return false;
            }
            wareSku.setStockLocked(wareSku.getStockLocked() + count);
            wareSkuList.add(wareSku);

            // 保存工作单详情（便于订单取消回溯）
            WareOrderTaskDetailEntity orderTaskDetail = WareOrderTaskDetailEntity.builder()
                    .skuId(skuId)
                    .skuName(wareSku.getSkuName())
                    .skuNum(count)
                    .wareId(wareSku.getWareId())
                    .lockStatus(StockEnum.LOCKED.getStatus()).build();
            orderTaskDetailList.add(orderTaskDetail);
        }

        // 锁定库存
        CompletableFuture<Void> wareLockFuture = CompletableFuture.runAsync(() -> {
            updateBatchById(wareSkuList);
        }, executor);
        // 保存工作单详情（便于订单取消回溯）
        CompletableFuture<Void> taskFuture = CompletableFuture.runAsync(() -> {
            orderTaskDetailService.saveBatch(orderTaskDetailList);
        }, executor);
        // 通知RabbitMQ将订单工作单保存起来
        CompletableFuture<Void> rabbitFuture = taskFuture.thenRunAsync(() -> {
            orderTaskDetailList.forEach(item -> {
                OrderTaskDetailDto orderTaskDetailDto = BeanUtils.transformFrom(item, OrderTaskDetailDto.class);
                Objects.requireNonNull(orderTaskDetailDto).setOrderSn(orderSn);
                rabbitTemplate.convertAndSend("stock-event-exchange", "stock.locked", orderTaskDetailDto);
            });
        }, executor);

        CompletableFuture.allOf(wareLockFuture, taskFuture, rabbitFuture);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void unlockInventory(@NonNull Long taskDetailId) {
        Assert.notNull(taskDetailId, "工作单详情id不能为空!");
        WareOrderTaskDetailEntity detail = orderTaskDetailService.getById(taskDetailId);
        if (detail == null) {
            throw new StockException("不存在该工作单详情!" + taskDetailId);
        }
        int res = baseMapper.unlockInventory(detail.getSkuId(), detail.getWareId(), detail.getSkuNum());
        if (res != 1) {
            throw new StockException("解锁库存失败!");
        }
        detail.setLockStatus(StockEnum.UNLOCKED.getStatus());
        orderTaskDetailService.updateById(detail);
    }

}
