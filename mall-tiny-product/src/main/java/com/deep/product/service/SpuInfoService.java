package com.deep.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deep.common.utils.PageUtils;
import com.deep.product.model.entity.SpuInfoEntity;

import java.util.Map;

/**
 * spu信息
 *
 * @author Deep
 * @date 2022/3/20
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    /**
     * 分页查询
     */
    PageUtils queryPage(Map<String, Object> params);
}