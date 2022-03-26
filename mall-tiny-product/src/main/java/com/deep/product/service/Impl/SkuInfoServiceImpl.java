package com.deep.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.common.utils.PageUtils;
import com.deep.common.utils.Query;
import com.deep.product.dao.SkuInfoDao;
import com.deep.product.model.entity.SkuImagesEntity;
import com.deep.product.model.entity.SkuInfoEntity;
import com.deep.product.model.entity.SpuInfoDescEntity;
import com.deep.product.model.vo.SkuItemSaleAttrVO;
import com.deep.product.model.vo.SkuItemVO;
import com.deep.product.model.vo.SpuItemAttrGroupVO;
import com.deep.product.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * sku信息
 *
 * @author Deep
 * @date 2022/3/20
 */
@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {
    @Autowired
    private SkuImagesService skuImagesService;
    @Autowired
    private AttrGroupService attrGroupService;
    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;
    @Autowired
    private SpuInfoDescService spuInfoDescService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<SkuInfoEntity> wrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (StringUtils.hasLength(key)) {
            wrapper.like("sku_name", key).or().like("sku_title", key);
        }
        IPage<SkuInfoEntity> page = this.page(new Query<SkuInfoEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }

    @Override
    public SkuItemVO queryItem(Long skuId) {
        Assert.notNull(skuId, "商品id不能为空!");
        SkuItemVO skuItemVO = new SkuItemVO();
        // 1.取sku实体
        SkuInfoEntity infoEntity = getById(skuId);
        skuItemVO.setInfo(infoEntity);
        // 2.sku图片信息
        List<SkuImagesEntity> imagesEntities = skuImagesService.getImagesBySkuId(skuId);
        skuItemVO.setImages(imagesEntities);

        // 3.获取sku销售属性组合
        List<SkuItemSaleAttrVO> saleAttrVOS =
                skuSaleAttrValueService.getSaleAttrsBySpuId(infoEntity.getSpuId());
        skuItemVO.setSaleAttr(saleAttrVOS);

        // 4.获取spu介绍
        SpuInfoDescEntity spuInfo = spuInfoDescService.getById(infoEntity.getSpuId());
        skuItemVO.setDesc(spuInfo);

        // 5.获取规格属性
        List<SpuItemAttrGroupVO> attrGroups =
                attrGroupService.getAttrGroupWithAttrs(infoEntity.getSpuId(), infoEntity.getCatalogId());
        skuItemVO.setGroupAttrs(attrGroups);
        return skuItemVO;
    }
}
