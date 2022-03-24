package com.deep.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.product.dao.ProductAttrValueDao;
import com.deep.product.model.entity.ProductAttrValueEntity;
import com.deep.product.service.ProductAttrValueService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * spu属性值
 *
 * @author Deep
 * @date 2022/3/20
 */
@Service("productAttrValueService")
public class ProductAttrValueServiceImpl extends ServiceImpl<ProductAttrValueDao, ProductAttrValueEntity> implements ProductAttrValueService {
    @Override
    public List<ProductAttrValueEntity> baseAttrsForSpu(Long spuId) {
        Assert.notNull(spuId, "spuId不能为空!");
        return list(new QueryWrapper<ProductAttrValueEntity>().eq("spu_id", spuId));
    }
}