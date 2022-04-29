package com.deep.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deep.product.model.entity.CategoryBrandRelationEntity;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * 品牌分类关联
 *
 * @author Deep
 * @date 2022/3/17
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    /**
     * 更新catName
     *
     * @param catId 分类id
     * @param name 分类名称
     */
    void updateCategory(@NonNull Long catId, String name);

    /**
     * 更新brandName
     *
     * @param brandId 品牌名
     * @param name 分类名称
     */
    void updateBrand(@NonNull Long brandId, String name);

    /**
     * 移除关联关系
     *
     * @param catIds 分类ids
     */
    void removeRelationsByCatIds(@NonNull List<Long> catIds);

    /**
     * 移除关联关系
     *
     * @param brandIds 品牌ids
     */
    void removeRelationsByBrandIds(@NonNull List<Long> brandIds);

    /**
     * 新增关联关系
     *
     * @param catId 分类id
     * @param brandId 品牌id
     */
    void saveRelation(@NonNull Long catId, @NonNull Long brandId);

    /**
     * 通过品牌id获取关联关系
     * 
     * @param brandId 品牌id
     * @return 关联关系集合
     */
    List<CategoryBrandRelationEntity> getRelationsByBrandId(@NonNull Long brandId);

    /**
     * 通过分类id获取关联关系
     * 
     * @param catId 分类id
     * @return 关联关系集合
     */
    List<CategoryBrandRelationEntity> getRelationsByCatId(@NonNull Long catId);
}
