package com.deep.ware.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.common.utils.PageUtils;
import com.deep.common.utils.Query;
import com.deep.ware.dao.PurchaseDemandDao;
import com.deep.ware.model.constant.WareConstant;
import com.deep.ware.model.entity.PurchaseDemandEntity;
import com.deep.ware.service.PurchaseDemandService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 采购需求
 *
 * @author Deep
 * @date 2022/3/28
 */
@Service("purchaseDetailService")
public class PurchaseDemandServiceImpl extends ServiceImpl<PurchaseDemandDao, PurchaseDemandEntity>
        implements PurchaseDemandService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<PurchaseDemandEntity> wrapper = new QueryWrapper<>();
        String wareId = (String) params.get("wareId");
        if (StringUtils.hasLength(wareId)) {
            wrapper.eq("ware_id", wareId);
        }
        String status = (String) params.get("status");
        if (StringUtils.hasLength(status)) {
            wrapper.eq("status", status);
        }
        // do nothing
        String key = (String) params.get("key");

        IPage<PurchaseDemandEntity> page = this.page(new Query<PurchaseDemandEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }

    @Override
    public void updatePurchaseId(@NonNull Long purchaseId, @NotEmpty List<Long> ids) {
        Assert.notNull(purchaseId, "采购单id不能为空!");
        Assert.notEmpty(ids, "采购项id集合不能为空!");

        List<PurchaseDemandEntity> demandEntities = ids.stream().map(item -> {
            PurchaseDemandEntity demandEntity = new PurchaseDemandEntity();
            demandEntity.setId(item);
            demandEntity.setPurchaseId(purchaseId);
            demandEntity.setStatus(WareConstant.PurchaseDemandStatusEnum.ASSIGNED.getCode());
            return demandEntity;
        }).collect(Collectors.toList());

        this.updateBatchById(demandEntities);
    }

    @Override
    public void updateStatusByPurId(Long purchaseId) {
        Assert.notNull(purchaseId, "采购单id不能为空!");
        this.update(new UpdateWrapper<PurchaseDemandEntity>()
                .eq("purchase_id", purchaseId)
                .set("status", WareConstant.PurchaseDemandStatusEnum.FINISH.getCode()));
    }

}
