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
    /**
     * 获取sku图片集
     * 
     * @param skuId 商品id
     * @return 图片集
     */
    List<SkuImagesEntity> getImagesBySkuId(@NonNull Long skuId);
}
