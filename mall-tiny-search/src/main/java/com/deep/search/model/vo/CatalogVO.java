package com.deep.search.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 分类vo
 *
 * @author Deep
 * @date 2022/3/23
 */
@Data
@AllArgsConstructor
public class CatalogVO {
    /**
     * 分类id
     */
    private Long catalogId;
    /**
     * 分类名称
     */
    private String catalogName;
}
