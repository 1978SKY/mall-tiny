package com.deep.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.product.dao.SpuImagesDao;
import com.deep.product.model.entity.SpuImagesEntity;
import com.deep.product.service.SpuImagesService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

/**
 * spu图片
 *
 * @author Deep
 * @date 2022/3/25
 */
@Service("spuImagesService")
public class SpuImagesServiceImpl extends ServiceImpl<SpuImagesDao, SpuImagesEntity> implements SpuImagesService {
    @Override
    public void saveImages(Long spuId, List<String> images) {
        Assert.notNull(spuId, "spuId不能为空!");
        Assert.notEmpty(images, "图片集合不能为空!");

        List<SpuImagesEntity> collect = images.stream().map(img -> {
            SpuImagesEntity imagesEntity = new SpuImagesEntity();
            imagesEntity.setSpuId(spuId);
            imagesEntity.setImgUrl(img);
            return imagesEntity;
        }).collect(Collectors.toList());

        this.saveBatch(collect);
    }
}
