package com.deep.product.model.params;

import com.deep.common.convert.InputConverter;
import com.deep.product.model.entity.AttrGroupEntity;
import lombok.Data;

/**
 * @author Deep
 * @date 2022/3/17
 */
@Data
public class AttrGroupParam implements InputConverter<AttrGroupEntity> {

    /**
     * 分组id
     */
    private Long attrGroupId;
    /**
     * 组名
     */
    private String attrGroupName;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 描述
     */
    private String descript;
    /**
     * 组图标
     */
    private String icon;
    /**
     * 所属分类id
     */
    private Long catelogId;
//    /**
//     * 完整目录
//     */
//    @TableField(exist = false)
//    private Long[] catelogPath;
}
