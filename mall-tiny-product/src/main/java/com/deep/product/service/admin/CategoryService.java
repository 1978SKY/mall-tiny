package com.deep.product.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deep.product.model.entity.CategoryEntity;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * 商品三级分类
 *
 * @author Deep
 * @date 2022/3/13
 */
public interface CategoryService extends IService<CategoryEntity> {
    /**
     * 以树形结构返回所有分类
     */
    List<CategoryEntity> getCategoryTree();

    /**
     * 更新分类
     */
    void updateCategory(CategoryEntity category);

    /**
     * 删除（展示状态置0）
     */
    void logicDelete(List<Long> catIds);

    /**
     * 获取分类所在路径
     */
    Long[] findCategoryPath(@NonNull Long catId);

    /**
     * 获取level层级的分类
     */
    List<CategoryEntity> getLevel(int level);
}
