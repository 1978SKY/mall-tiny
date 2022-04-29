package com.deep.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.product.dao.SkuImagesDao;
import com.deep.product.model.entity.SkuImagesEntity;
import com.deep.product.service.SkuImagesService;
import org.springframework.lang.NonNull;
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
    public List<SkuImagesEntity> getImagesBySkuId(@NonNull Long skuId) {
        Assert.notNull(skuId, "skuId不能为空!");

        return list(new QueryWrapper<SkuImagesEntity>().eq("sku_id", skuId));
    }
}
