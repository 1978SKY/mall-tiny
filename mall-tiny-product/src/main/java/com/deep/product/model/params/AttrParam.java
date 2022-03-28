package com.deep.product.model.params;

import com.deep.common.convert.InputConverter;
import com.deep.product.model.entity.AttrEntity;
import lombok.Data;

/**
 * 规格属性参数
 *
 * @author Deep
 * @date 2022/3/27
 */
@Data
public class AttrParam implements InputConverter<AttrEntity> {
    /**
     * 分组id
     */
    private Long attrGroupId;
    /**
     * 属性名称
     */
    private String attrName;
    /**
     * 属性类型（基础属性、销售属性）
     */
    private Integer attrType;
    /**
     * 分类id
     */
    private Long catelogId;
    /**
     * 启用状态[0 - 禁用，1 - 启用]
     */
    private Long enable;
    /**
     * 图标地址
     */
    private String icon;
    /**
     * 是否需要检索[0-不需要，1-需要]
     */
    private Integer searchType;
    /**
     * 快速展示【是否展示在介绍上；0-否 1-是】，在sku中仍然可以调整
     */
    private Integer showDesc;
    /**
     * 可选值列表（分号相隔）
     */
    private String valueSelect;
    /**
     * 值类型（单值、多值）
     */
    private Integer valueType;
}
