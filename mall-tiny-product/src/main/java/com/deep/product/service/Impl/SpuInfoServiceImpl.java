package com.deep.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.common.utils.PageUtils;
import com.deep.common.utils.Query;
import com.deep.product.dao.SpuInfoDao;
import com.deep.product.model.entity.SpuInfoEntity;
import com.deep.product.service.SpuInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * spu信息
 *
 * @author Deep
 * @date 2022/3/20
 */
@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<SpuInfoEntity> wrapper = new QueryWrapper<>();

        Long brandId = (Long) params.get("brandId");
        if (brandId != null && brandId > 0) {
            wrapper.eq("brand_id", brandId);
        }
        Long catId = (Long) params.get("catelogId");
        if (catId != null && catId > 0) {
            wrapper.eq("catalog_id", catId);
        }
        Integer status = (Integer) params.get("status");
        if (status != null && status > 0) {
            wrapper.eq("publish_status", status);
        }
        String key = (String) params.get("key");
        if (StringUtils.hasLength(key)) {
            wrapper.like("spu_name", key);
        }
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }
}
