package com.deep.product.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deep.product.model.entity.SpuImagesEntity;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * spu图片
 *
 * @author Deep
 * @date 2022/3/25
 */
public interface SpuImagesService extends IService<SpuImagesEntity> {
    /**
     * 保存spu图片
     */
    void saveImages(@NonNull Long spuId, @NonNull List<String> images);
}
