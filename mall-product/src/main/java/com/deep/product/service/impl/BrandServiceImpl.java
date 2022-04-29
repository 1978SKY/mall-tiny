package com.deep.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.common.utils.PageUtils;
import com.deep.common.utils.Query;
import com.deep.product.dao.BrandDao;
import com.deep.product.model.entity.BrandEntity;
import com.deep.product.service.BrandService;
import com.deep.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

/**
 * 品牌管理
 *
 * @author Deep
 * @date 2022/3/15
 */
@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    @Autowired
    private CategoryBrandRelationService relationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<BrandEntity> wrapper = new QueryWrapper<>();

        String key = (String)params.get("key");
        if (StringUtils.hasLength(key)) {
            wrapper.eq("first_letter", key).or().like("name", key);
        }
        IPage<BrandEntity> page = this.page(new Query<BrandEntity>().getPage(params), wrapper);

        return new PageUtils(page);
    }

    @Override
    public void updateStatus(@NonNull Long brandId, Integer showStatus) {
        Assert.notNull(brandId, "品牌id不能为空!");
        this.update(new UpdateWrapper<BrandEntity>().eq("brand_id", brandId).set("show_status", showStatus));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeBatch(@NotEmpty List<Long> ids) {
        Assert.notEmpty(ids, "品牌Ids不能为空!");
        this.removeByIds(ids);
        // 删除冗余表
        relationService.removeRelationsByBrandIds(ids);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(BrandEntity brandEntity) {
        this.updateById(brandEntity);
        // 更新关联类
        relationService.updateBrand(brandEntity.getBrandId(), brandEntity.getName());
    }

}
