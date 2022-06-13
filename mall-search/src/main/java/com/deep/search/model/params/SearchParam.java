package com.deep.search.model.params;

import lombok.Data;

import java.util.List;

/**
 * 检索参数
 *
 * @author Deep
 * @date 2022/3/21
 */
@Data
public class SearchParam {
    /**
     * 检索关键字
     */
    private String keyword;
    /**
     * 分类id
     */
    private Long catId;
    /**
     * 排序：sort=saleCount_asc、sort=hotScore_asc、sort=skuPrice_asc
     */
    private String sort;
    /**
     * 是否有货,0无货,1有货
     */
    private Integer hasStock;
    /**
     * 商品价格区间 0_500/500_/_500
     */
    private String skuPrice;
    /**
     * 品牌id
     */
    private List<Long> brandId;
    /**
     * 商品属性 attrs=["id_val","id_val"...]
     */
    private List<String> attrs;
    /**
     * 原生的所有查询条件
     */
    private String _queryString;

    /**
     * 页码
     */
    private Integer pageNum = 1;
    /**
     * 每页数量
     */
    private Integer pageSize = 6;
}
