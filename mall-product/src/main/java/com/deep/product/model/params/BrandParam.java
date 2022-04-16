package com.deep.product.model.params;

import com.deep.common.convert.InputConverter;
import com.deep.product.model.entity.BrandEntity;
import lombok.Data;

/**
 * 品牌参数
 *
 * @author Deep
 * @date 2022/3/16
 */
@Data
public class BrandParam implements InputConverter<BrandEntity> {
    /**
     * 品牌id
     */
    private Long brandId;
    /**
     * 品牌名
     */
    private String name;
    /**
     * 品牌logo地址
     */
    private String logo;
    /**
     * 介绍
     */
    private String descript;
    /**
     * 显示状态[0-不显示；1-显示]
     */
    private Integer showStatus;
    /**
     * 检索首字母
     */
    private String firstLetter;
    /**
     * 排序
     */
    private Integer sort;
}
