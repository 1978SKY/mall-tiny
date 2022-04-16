package com.deep.product.model.params;

import com.deep.common.convert.InputConverter;
import com.deep.product.model.entity.SkuImagesEntity;
import lombok.Data;

/**
 * sku图片集
 *
 * @author Deep
 * @date 2022/3/25
 */
@Data
public class SkuImageParam implements InputConverter<SkuImagesEntity> {
    /**
     * 图片url
     */
    private String imgUrl;
    /**
     * 默认图片
     */
    private int defaultImg;
}
