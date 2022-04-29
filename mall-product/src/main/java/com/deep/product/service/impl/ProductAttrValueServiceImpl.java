package com.deep.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.product.dao.ProductAttrValueDao;
import com.deep.product.model.entity.AttrEntity;
import com.deep.product.model.entity.ProductAttrValueEntity;
import com.deep.product.model.params.SpuSaveParam;
import com.deep.product.service.ProductAttrValueService;
import com.deep.product.service.AttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * spu属性值
 *
 * @author Deep
 * @date 2022/3/20
 */
@Service("productAttrValueService")
public class ProductAttrValueServiceImpl extends ServiceImpl<ProductAttrValueDao, ProductAttrValueEntity>
        implements ProductAttrValueService {

    @Autowired
    private AttrService attrService;

    @Override
    public List<ProductAttrValueEntity> baseAttrsForSpu(Long spuId) {
        Assert.notNull(spuId, "spuId不能为空!");
        return list(new QueryWrapper<ProductAttrValueEntity>().eq("spu_id", spuId));
    }

    @Override
    public void saveAttrs(Long spuId, List<SpuSaveParam.SpuAttrParam> spuAttrParams) {
        Assert.notNull(spuId, "spuId不能为空!");
        Assert.notEmpty(spuAttrParams, "spu属性规格不能为空!");

        List<Long> attrIds = spuAttrParams.stream().map(SpuSaveParam.SpuAttrParam::getAttrId).collect(Collectors.toList());
        Map<Long, String> nameMap = attrService.listByIds(attrIds).stream()
                .collect(Collectors.toMap(AttrEntity::getAttrId, AttrEntity::getAttrName));

        List<ProductAttrValueEntity> collect = spuAttrParams.stream().map(item -> {
            ProductAttrValueEntity attrValueEntity = new ProductAttrValueEntity();
            attrValueEntity.setSpuId(spuId);
            attrValueEntity.setAttrId(item.getAttrId());
            attrValueEntity.setAttrName(nameMap.get(item.getAttrId()));
            attrValueEntity.setAttrValue(item.getAttrValues());
            attrValueEntity.setQuickShow(item.getShowDesc());
            attrValueEntity.setAttrSort(0);
            return attrValueEntity;
        }).collect(Collectors.toList());

        saveBatch(collect);
    }
}