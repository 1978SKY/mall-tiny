package com.deep.product.model.params;

import lombok.Data;

/**
 * sku图片集
 *
 * @author Deep
 * @date 2022/3/25
 */
@Data
public class SkuImageParam {
    /**
     * 图片url
     */
    private String imgUrl;
    /**
     * 默认图片
     */
    private int defaultImg;
}
