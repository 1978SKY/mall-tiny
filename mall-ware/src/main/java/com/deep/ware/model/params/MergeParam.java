package com.deep.ware.model.params;

import lombok.Data;

import java.util.List;

/**
 * 合并整单参数
 *
 * @author Deep
 * @date 2022/3/28
 */
@Data
public class MergeParam {
    /**
     * 采购单id
     */
    private Long purchaseId;
    /**
     * 采购项（需要合并的采购需求id）
     */
    private List<Long> items;
}
