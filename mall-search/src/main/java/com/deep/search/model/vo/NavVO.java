package com.deep.search.model.vo;

import lombok.Data;

/**
 * 面包屑导航vo
 *
 * @author Deep
 * @date 2022/3/23
 */
@Data
public class NavVO {
    /**
     * 属性名
     */
    private String navName;
    /**
     * 属性值
     */
    private String navValue;
    /**
     * 链接地址（取消了之后要调到那个地方）
     */
    private String link;
}
