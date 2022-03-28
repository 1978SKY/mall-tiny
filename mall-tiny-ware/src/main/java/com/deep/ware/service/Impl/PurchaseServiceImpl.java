package com.deep.ware.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.common.utils.PageUtils;
import com.deep.common.utils.Query;
import com.deep.ware.dao.PurchaseDao;
import com.deep.ware.model.constant.WareConstant;
import com.deep.ware.model.entity.PurchaseDemandEntity;
import com.deep.ware.model.entity.PurchaseEntity;
import com.deep.ware.service.PurchaseDemandService;
import com.deep.ware.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 采购单
 *
 * @author Deep
 * @date 2022/3/28
 */
@Service("purchaseService")
public class PurchaseServiceImpl extends ServiceImpl<PurchaseDao, PurchaseEntity> implements PurchaseService {
    @Autowired
    private PurchaseDemandService demandService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<PurchaseEntity> wrapper = new QueryWrapper<>();
        String status = (String) params.get("status");
        if (StringUtils.hasLength(status)) {
            // 按照采购单状态过滤
            wrapper.eq("status", status);
        }
        String key = (String) params.get("key");
        if (StringUtils.hasLength(key)) {
            // 按照采购单id或分配员姓名查找
            wrapper.eq("id", key).or().like("assignee_name", key);
        }
        IPage<PurchaseEntity> page =
                this.page(new Query<PurchaseEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }

    @Override
    public PageUtils queryUnreceivedPage(Map<String, Object> params) {
        QueryWrapper<PurchaseEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("status", WareConstant.PurchaseStatusEnum.CREATED.getCode()).or()
                .eq("status", WareConstant.PurchaseStatusEnum.ASSIGNED.getCode());
        IPage<PurchaseEntity> page = this.page(new Query<PurchaseEntity>().getPage(params), wrapper);

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void merge(Long purchaseId, List<Long> items) {
        Assert.notEmpty(items, "采购项不能为空!");
        if (purchaseId == null) {
            // create purchase order and receive
            PurchaseEntity purchaseEntity = new PurchaseEntity();
            purchaseEntity.setStatus(WareConstant.PurchaseStatusEnum.RECEIVE.getCode());
            this.save(purchaseEntity);
            purchaseId = purchaseEntity.getId();
        } else {
            this.update(new UpdateWrapper<PurchaseEntity>()
                    .eq("status", WareConstant.PurchaseStatusEnum.RECEIVE.getCode()));
        }
        demandService.updatePurchaseId(purchaseId, items);
    }

    @Transactional
    @Override
    public void finishPurchase(List<Long> ids) {
        Assert.notEmpty(ids, "订单id集合不能为空!");
        List<PurchaseEntity> purchaseEntities = this.listByIds(ids);
        purchaseEntities.stream().forEach(item -> {
            demandService.updateStatusByPurId(item.getId());
            this.update(new UpdateWrapper<PurchaseEntity>()
                    .eq("id", item.getId())
                    .set("status", WareConstant.PurchaseStatusEnum.FINISH.getCode()));
        });
    }
}
