package com.deep.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.product.dao.SkuSaleAttrValueDao;
import com.deep.product.model.entity.SkuSaleAttrValueEntity;
import com.deep.product.model.vo.SkuItemSaleAttrVO;
import com.deep.product.service.SkuSaleAttrValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * sku销售属性
 *
 * @author Deep
 * @date 2022/3/24
 */
@Service("skuSaleAttrValueService")
public class SkuSaleAttrValueServiceImpl extends ServiceImpl<SkuSaleAttrValueDao, SkuSaleAttrValueEntity> implements SkuSaleAttrValueService {
    @Autowired
    private SkuSaleAttrValueDao skuSaleAttrValueDao;

    @Override
    public List<SkuItemSaleAttrVO> getSaleAttrsBySpuId(Long spuId) {
        Assert.notNull(spuId, "spuId不能为空!");

        return skuSaleAttrValueDao.getSaleAttrBySpuId(spuId);
    }

    @Override
    public List<SkuSaleAttrValueEntity> getSaleAttrsBySkuId(Long skuId) {
        Assert.notNull(skuId, "商品id不能为空!");
        QueryWrapper<SkuSaleAttrValueEntity> wrapper = new QueryWrapper<>();
        return list(wrapper.eq("sku_id", skuId));
    }
}
