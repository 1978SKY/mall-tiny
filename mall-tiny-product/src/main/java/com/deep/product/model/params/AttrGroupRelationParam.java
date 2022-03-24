package com.deep.product.model.params;

import lombok.Data;

/**
 * 属性&属性分组关联参数
 *
 * @author Deep
 * @date 2022/3/19
 */
@Data
public class AttrGroupRelationParam {
    /**
     * 品牌id
     */
    private Long attrId;
    /**
     * 分类id
     */
    private Long attrGroupId;
}
