package com.deep.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deep.product.model.entity.ProductAttrValueEntity;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * spu属性值
 *
 * @author Deep
 * @date 2022/3/20
 */
public interface ProductAttrValueService extends IService<ProductAttrValueEntity> {
    /**
     * spu基础属性
     */
    List<ProductAttrValueEntity> baseAttrsForSpu(@NonNull Long spuId);
}
