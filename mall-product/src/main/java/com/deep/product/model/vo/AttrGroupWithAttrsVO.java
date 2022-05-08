package com.deep.product.model.vo;

import com.deep.product.model.entity.AttrEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 属性分组包含分组下的所有属性
 *
 * @author Deep
 * @date 2022/3/20
 */
@Data
public class AttrGroupWithAttrsVO {
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

    /**
     * 保存整个实体信息
     */
    private List<AttrEntity> attrs;
}
