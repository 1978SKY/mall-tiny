package com.deep.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.product.dao.CategoryDao;
import com.deep.product.model.entity.CategoryEntity;
import com.deep.product.service.CategoryBrandRelationService;
import com.deep.product.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 商品三级分类
 *
 * @author Deep
 * @date 2022/3/13
 */
@Slf4j
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private CategoryBrandRelationService relationService;

    // 先从缓存中查询数据，如果缓存没有则执行方法
    @Cacheable(value = "product:category", key = "#root.methodName")
    @Override
    public List<CategoryEntity> getCategoryTree() {
        return categoryFromDB();
    }

    // 删除所有缓存
    @CacheEvict(value = "product:category", allEntries = true)
    @Transactional
    @Override
    public void updateCategory(CategoryEntity category) {
        this.updateById(category);
        // 更新关联表
        if (StringUtils.hasLength(category.getName())) {
            relationService.updateCategory(category.getCatId(), category.getName());
        }
    }

    @Transactional
    @Override
    public void logicDelete(List<Long> catIds) {
        categoryDao.logicDelete(catIds);
        // 删除关联表
        relationService.removeRelationsByCatIds(catIds);
    }

    @Override
    public Long[] findCategoryPath(Long catId) {
        Assert.notNull(catId, "分类id不能为空!");
        List<Long> path = new ArrayList<>();
        findCategoryPath(path, catId);
        Collections.reverse(path);

        Long[] pathArray = new Long[path.size()];
        return path.toArray(pathArray);
    }


    @Cacheable(value = {"product:category"}, key = "#root.methodName")
    @Override
    public List<CategoryEntity> getLevel(int level) {
        if (level <= 0) {
            throw new IllegalArgumentException("Level must greater than 0,but it's:" + level);
        }
        return list(new QueryWrapper<CategoryEntity>().eq("cat_level", level));
    }

    /**
     * 从数据库中获取商品分类
     *
     * @return 分类集合
     */
    private List<CategoryEntity> categoryFromDB() {
        // 获取所有展示商品
        List<CategoryEntity> categories =
                baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("show_status", 1));
        // 组装成树形结构  --> 平均0~1ms
        Map<Long, CategoryEntity> categoryMap = categories.stream().collect(Collectors.toMap(CategoryEntity::getCatId, v -> v));
        LinkedList<CategoryEntity> result = new LinkedList<>();
        for (CategoryEntity value : categoryMap.values()) {
            Long parentCid = value.getParentCid();
            if (parentCid != null && parentCid > 0L) {
                CategoryEntity pCategoryVO = categoryMap.get(parentCid);
                List<CategoryEntity> children = pCategoryVO.getChildren();
                if (children == null) {
                    children = new LinkedList<>();
                }
                children.add(value);
                pCategoryVO.setChildren(children);
            }
            if (value.getCatLevel() == 1) {
                result.add(value);
            }
        }
        return result;
    }

    /**
     * 递归查找父节点
     *
     * @param path  收集父节点的集合
     * @param catId 当前节点
     */
    private void findCategoryPath(List<Long> path, Long catId) {
        path.add(catId);
        CategoryEntity entity = getById(catId);
        if (entity.getParentCid() > 0) {     // 有父节点
            findCategoryPath(path, entity.getParentCid());
        }
    }
}
