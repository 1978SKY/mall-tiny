package com.deep.product.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deep.product.model.entity.ProductAttrValueEntity;
import com.deep.product.model.params.SpuSaveParam;
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

    void saveAttrs(@NonNull Long spuId, @NonNull List<SpuSaveParam.SpuAttrParam> spuAttrParams);
}
