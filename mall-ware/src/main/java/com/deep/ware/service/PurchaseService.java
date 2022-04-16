package com.deep.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deep.common.utils.PageUtils;
import com.deep.ware.model.entity.PurchaseEntity;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

/**
 * 采购单
 *
 * @author Deep
 * @date 2022/3/28
 */
public interface PurchaseService extends IService<PurchaseEntity> {
    /**
     * 获取采购单列表
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 获取未分配采购单列表
     */
    PageUtils queryUnreceivedPage(Map<String, Object> params);

    /**
     * 合并采购需求
     */
    void merge(Long purchaseId, @NonNull List<Long> items);

    /**
     * 完成采购
     */
    void finishPurchase(@NotEmpty List<Long> ids);
}
