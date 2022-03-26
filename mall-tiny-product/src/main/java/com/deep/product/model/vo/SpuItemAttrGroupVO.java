package com.deep.product.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 属性规格分组
 *
 * @author Deep
 * @date 2022/3/24
 */
@Data
@Accessors(chain = true)
public class SpuItemAttrGroupVO {
    /**
     * 属性分组名称
     */
    private String groupName;
    /**
     * 基础属性
     */
    private List<SpuBaseAttrVO> attrs;

    @Data
    @AllArgsConstructor
    @Accessors(chain = true)
    public static class SpuBaseAttrVO {
        /**
         * 基础属性名称
         */
        private String attrName;
        /**
         * 基础属性值
         */
        private String attrValue;
    }
}
