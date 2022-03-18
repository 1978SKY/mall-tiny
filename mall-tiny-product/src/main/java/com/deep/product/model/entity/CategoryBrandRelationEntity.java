package com.deep.product.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 品牌分类关联
 *
 * @author deep
 * @email ${email}
 * @date 2022-01-13 17:09:54
 */
@Data
@TableName("pms_category_brand_relation")
public class CategoryBrandRelationEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 品牌id
     */
    private Long brandId;
    /**
     * 分类id
     */
    private Long catId;
    /**
     * 品牌名
     */
    private String brandName;
    /**
     * 分类名
     */
    private String catName;
}
