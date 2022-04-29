package com.deep.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.product.dao.CategoryBrandRelationDao;
import com.deep.product.model.entity.BrandEntity;
import com.deep.product.model.entity.CategoryBrandRelationEntity;
import com.deep.product.model.entity.CategoryEntity;
import com.deep.product.service.BrandService;
import com.deep.product.service.CategoryBrandRelationService;
import com.deep.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 品牌分类关联
 *
 * @author Deep
 * @date 2022/3/17
 */
@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity>
    implements CategoryBrandRelationService {

    @Autowired
    private BrandService brandService;
    @Autowired
    private CategoryService categoryService;

    @Override
    public void updateCategory(@NonNull Long catId, String name) {
        Assert.notNull(catId, "catId不能为空!");
        this.update(new UpdateWrapper<CategoryBrandRelationEntity>().eq("catelog_id", catId).set("catelog_name", name));
    }

    @Override
    public void updateBrand(@NonNull Long brandId, String name) {
        Assert.notNull(brandId, "brandId不能为空!");
        this.update(new UpdateWrapper<CategoryBrandRelationEntity>().eq("brand_id", brandId).set("brand_name", name));
    }

    @Override
    public void removeRelationsByCatIds(@NonNull List<Long> catIds) {
        Assert.notEmpty(catIds, "分类ids不能为空!");
        QueryWrapper<CategoryBrandRelationEntity> wrapper = new QueryWrapper<>();
        for (Long catId : catIds) {
            wrapper.eq("catelog_id", catId).or();
        }
        this.remove(wrapper);
    }

    @Override
    public void removeRelationsByBrandIds(@NonNull List<Long> brandIds) {
        Assert.notEmpty(brandIds, "分类ids不能为空!");
        QueryWrapper<CategoryBrandRelationEntity> wrapper = new QueryWrapper<>();
        for (Long brandId : brandIds) {
            wrapper.eq("brand_id", brandId).or();
        }
        this.remove(wrapper);
    }

    @Override
    public void saveRelation(@NonNull Long catId, @NonNull Long brandId) {
        Assert.notNull(catId, "分类Id不能为空!");
        Assert.notNull(brandId, "品牌Id不能为空!");

        CategoryBrandRelationEntity relationEntity = new CategoryBrandRelationEntity();
        CategoryEntity categoryEntity = categoryService.getById(catId);
        BrandEntity brandEntity = brandService.getById(brandId);
        relationEntity.setCatelogId(catId);
        relationEntity.setBrandId(brandId);
        relationEntity.setCatelogName(categoryEntity.getName());
        relationEntity.setBrandName(brandEntity.getName());

        this.save(relationEntity);
    }

    @Override
    public List<CategoryBrandRelationEntity> getRelationsByBrandId(@NonNull Long brandId) {
        Assert.notNull(brandId, "品牌id不能为空!");
        QueryWrapper<CategoryBrandRelationEntity> wrapper = new QueryWrapper<>();

        return list(wrapper.eq("brand_id", brandId));
    }

    @Override
    public List<CategoryBrandRelationEntity> getRelationsByCatId(@NonNull Long catId) {
        Assert.notNull(catId, "分类id不能为空!");
        QueryWrapper<CategoryBrandRelationEntity> wrapper = new QueryWrapper<>();

        return list(wrapper.eq("catelog_id", catId));
    }
}
