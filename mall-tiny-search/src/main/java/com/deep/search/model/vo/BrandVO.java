package com.deep.search.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 品牌vo
 *
 * @author Deep
 * @date 2022/3/23
 */
@Data
@AllArgsConstructor
public class BrandVO {
    /**
     * 品牌id
     */
    private Long brandId;
    /**
     * 品牌名称
     */
    private String brandName;
    /**
     * 品牌logo地址
     */
    private String brandImg;
}
