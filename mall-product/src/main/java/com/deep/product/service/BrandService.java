package com.deep.product.service;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotEmpty;

import org.springframework.lang.NonNull;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deep.common.utils.PageUtils;
import com.deep.product.model.entity.BrandEntity;

/**
 * 品牌管理
 *
 * @author Deep
 * @date 2022/3/15
 */
public interface BrandService extends IService<BrandEntity> {
    /**
     * 分页查询
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 修改显式状态
     */
    void updateStatus(@NonNull Long brandId, Integer showStatus);

    /**
     * 批量删除品牌
     *
     * @param Ids 品牌id
     */
    void removeBatch(@NotEmpty List<Long> ids);

    /**
     * 修改品牌
     *
     * @param brandEntity 品牌实体
     */
    void update(BrandEntity brandEntity);

}
