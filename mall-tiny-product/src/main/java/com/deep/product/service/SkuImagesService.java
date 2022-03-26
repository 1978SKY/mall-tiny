package com.deep.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deep.product.model.entity.SkuImagesEntity;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * sku图片
 *
 * @author Deep
 * @date 2022/3/24
 */
public interface SkuImagesService extends IService<SkuImagesEntity> {
    List<SkuImagesEntity> getImagesBySkuId(@NonNull Long skuId);
}
