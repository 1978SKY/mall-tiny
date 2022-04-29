package com.deep.product.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.deep.product.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deep.common.utils.BeanUtils;
import com.deep.product.model.entity.SkuInfoEntity;
import com.deep.product.model.vo.ProductVo;
import com.deep.product.service.SkuInfoService;

/**
 * 商城首页
 *
 * @author Deep
 * @date 2022/4/8
 */
@Service("indexService")
public class IndexServiceImpl implements IndexService {
    @Autowired
    private SkuInfoService skuInfoService;

    @Override
    public List<SkuInfoEntity> getProducts() {
        return skuInfoService.list();
    }

    @Override
    public List<ProductVo> getTrendingProduct(int count) {
        QueryWrapper<SkuInfoEntity> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("sale_count").last("limit " + count);
        List<SkuInfoEntity> skus = skuInfoService.list(wrapper);

        return BeanUtils.transformFromInBatch(skus, ProductVo.class);
    }

    @Override
    public List<List<ProductVo>> getSelectProduct(int pageCount, int count) {
        QueryWrapper<SkuInfoEntity> wrapper = new QueryWrapper<>();

        int total = pageCount * count;
        wrapper.last("limit " + total);
        List<SkuInfoEntity> skus = skuInfoService.list(wrapper);

        List<List<ProductVo>> res = new ArrayList<>();
        for (int i = 0; i < pageCount; i++) {
            int index = i * count;
            List<ProductVo> skuVos =
                BeanUtils.transformFromInBatch(skus.subList(index, index + count), ProductVo.class);
            res.add(skuVos);
        }
        return res;
    }

    @Override
    public List<ProductVo> getBestSaleEveryWeek(int count) {
        QueryWrapper<SkuInfoEntity> wrapper = new QueryWrapper<>();
        wrapper.last("order by rand() limit " + count);
        List<SkuInfoEntity> skus = skuInfoService.list(wrapper);

        return BeanUtils.transformFromInBatch(skus, ProductVo.class);
    }
}
