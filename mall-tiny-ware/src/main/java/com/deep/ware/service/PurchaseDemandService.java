package com.deep.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deep.common.utils.PageUtils;
import com.deep.ware.model.entity.PurchaseDemandEntity;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

/**
 * 采购需求
 *
 * @author Deep
 * @date 2022/3/28
 */
public interface PurchaseDemandService extends IService<PurchaseDemandEntity> {
    /**
     * 获取采购需求
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 批量更新采购需求中采购单id
     */
    void updatePurchaseId(@NonNull Long purchaseId, @NotEmpty List<Long> ids);

    /**
     * 通过采购单更新采购需求状态
     */
    void updateStatusByPurId(@NonNull Long purchaseId);
}
