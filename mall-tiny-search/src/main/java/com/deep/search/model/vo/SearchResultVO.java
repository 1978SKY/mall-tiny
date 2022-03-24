package com.deep.search.model.vo;

import com.deep.search.model.dto.SkuEsDTO;
import lombok.Data;

import java.util.List;

/**
 * 检索结果
 *
 * @author Deep
 * @date 2022/3/21
 */
@Data
public class SearchResultVO {
    /**
     * 检索到的商品
     */
    private List<SkuEsDTO> products;
    // -------------分页信息-------------
    /**
     * 页号
     */
    private Integer pageNum;
    /**
     * 总页
     */
    private Integer totalPages;
    /**
     * 总记录数
     */
    private long total;
    /**
     * 允许页数集合[1、2、3、4、5]
     */
    private List<Integer> pageNavs;
    // ----------品牌、分类、属性-----------
    /**
     * 聚合分析出的品牌
     */
    private List<BrandVO> brands;
    /**
     * 聚合分析出的分类
     */
    private List<CatalogVO> catalogs;
    /**
     * 聚合分析出的属性
     */
    private List<AttrVO> attrs;

    // ------------面包屑导航-------------
    private List<NavVO> navs;
    private List<Long> attrIds;
}
