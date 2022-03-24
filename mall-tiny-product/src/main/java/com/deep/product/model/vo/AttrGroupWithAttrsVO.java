package com.deep.product.model.vo;

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
     * 所属分类id
     */
    private Long catelogId;
    /**
     * 属性信息
     */
    private List<AttrVO> attrs;

    @Data
    @AllArgsConstructor
    public static class AttrVO{
        /**
         * 属性id
         */
        private Long attrId;
        /**
         * 属性名
         */
        private String attrName;
    }
}
