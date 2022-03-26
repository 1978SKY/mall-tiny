package com.deep.product.service.Impl;

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
}
