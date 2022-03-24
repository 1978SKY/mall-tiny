package com.deep.product.model.vo;

import lombok.Data;

/**
 * 商品属性规格（响应给前端数据）
 *
 * @author Deep
 * @date 2022/3/18
 */
@Data
public class AttrRespVO extends AttrVO {
    /**
     * 所属分类
     */
    private String catelogName;
    /**
     * 所属分组
     */
    private String groupName;
}
