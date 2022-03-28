package com.deep.ware.model.params;

import com.deep.common.convert.InputConverter;
import com.deep.ware.model.entity.WareInfoEntity;
import lombok.Data;

/**
 * 仓库参数
 *
 * @author Deep
 * @date 2022/3/28
 */
@Data
public class WareInfoParam implements InputConverter<WareInfoEntity> {
    /**
     * 仓库名
     */
    private String name;
    /**
     * 仓库地址
     */
    private String address;
    /**
     * 区域编码
     */
    private String areacode;
}
