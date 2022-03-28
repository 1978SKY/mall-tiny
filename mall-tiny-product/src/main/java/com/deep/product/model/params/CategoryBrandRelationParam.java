package com.deep.product.model.params;

import com.deep.common.convert.InputConverter;
import com.deep.product.model.entity.CategoryBrandRelationEntity;
import lombok.Data;

/**
 * 品牌分类关联
 *
 * @author Deep
 * @date 2022/3/17
 */
@Data
public class CategoryBrandRelationParam implements InputConverter<CategoryBrandRelationEntity> {
    /**
     * 品牌id
     */
    private Long brandId;
    /**
     * 分类id
     */
    private Long catelogId;
}
