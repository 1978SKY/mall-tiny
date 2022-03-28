package com.deep.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deep.common.utils.PageUtils;
import com.deep.product.model.entity.SkuInfoEntity;
import com.deep.product.model.params.SkuParam;
import com.deep.product.model.params.SpuSaveParam;
import com.deep.product.model.vo.SkuItemVO;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Map;

/**
 * sku信息
 *
 * @author Deep
 * @date 2022/3/20
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {
    /**
     * 分页查询
     */
    PageUtils queryPage(Map<String, Object> params);
    /**
     * 商品查询
     */
    SkuItemVO queryItem(@NonNull Long skuId);
    /**
     * 保存skus
     */
    void savSkuParams(@NonNull Long spuId,@NonNull SpuSaveParam spuSaveParam);
}
