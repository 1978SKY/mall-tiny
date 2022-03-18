package com.deep.product.model.params;

import com.baomidou.mybatisplus.annotation.TableId;
import com.deep.common.convert.InputConverter;
import com.deep.product.model.entity.CategoryEntity;
import lombok.Data;

/**
 * 分类参数
 *
 * @author Deep
 * @date 2022/3/17
 */
@Data
public class CategoryParam implements InputConverter<CategoryEntity> {
    /**
     * 分类id
     */
    @TableId
    private Long catId;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 父分类id
     */
    private Long parentCid;
    /**
     * 层级
     */
    private Integer catLevel;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 图标地址
     */
    private String icon;
    /**
     * 计量单位
     */
    private String productUnit;
}
