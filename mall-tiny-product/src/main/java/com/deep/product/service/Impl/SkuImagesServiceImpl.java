package com.deep.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.product.dao.SkuImagesDao;
import com.deep.product.model.entity.SkuImagesEntity;
import com.deep.product.service.SkuImagesService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * sku图片
 *
 * @author Deep
 * @date 2022/3/24
 */
@Service("skuImagesService")
public class SkuImagesServiceImpl extends ServiceImpl<SkuImagesDao, SkuImagesEntity> implements SkuImagesService {
    @Override
    public List<SkuImagesEntity> getImagesBySkuId(Long skuId) {
        Assert.notNull(skuId, "skuId不能为空!");

        return baseMapper.selectList(new QueryWrapper<SkuImagesEntity>().eq("sku_id", skuId));
    }
}
