package com.deep.search.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 属性vo
 *
 * @author Deep
 * @date 2022/3/23
 */
@Data
@AllArgsConstructor
public class AttrVO {
    /**
     * 属性id
     */
    private Long attrId;
    /**
     * 属性名称
     */
    private String attrName;
    /**
     * 属性值
     */
    private List<String> attrValue;
}
