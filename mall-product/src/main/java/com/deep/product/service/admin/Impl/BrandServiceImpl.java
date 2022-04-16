package com.deep.product.service.admin.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.common.utils.PageUtils;
import com.deep.common.utils.Query;
import com.deep.product.dao.BrandDao;
import com.deep.product.model.entity.BrandEntity;
import com.deep.product.service.admin.BrandService;
import com.deep.product.service.admin.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

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

        String key = (String) params.get("key");
        if (StringUtils.hasLength(key)) {
            wrapper.eq("first_letter", key).or().like("name", key);
        }
        IPage<BrandEntity> page = this.page(new Query<BrandEntity>().getPage(params), wrapper);

        return new PageUtils(page);
    }

    @Override
    public void updateStatus(Long brandId, Integer showStatus) {
        this.update(new UpdateWrapper<BrandEntity>()
                .eq("brand_id", brandId)
                .set("show_status", showStatus));
    }

    @Transactional
    @Override
    public void removeBatch(List<Long> Ids) {
        Assert.notNull(Ids, "品牌Ids不能为空!");
        this.removeByIds(Ids);
        // 删除冗余表
        relationService.removeRelationsByBrandIds(Ids);
    }

    @Transactional
    @Override
    public void update(BrandEntity brandEntity) {
        this.updateById(brandEntity);
        // 更新关联类
        relationService.updateBrand(brandEntity.getBrandId(), brandEntity.getName());
    }

}
