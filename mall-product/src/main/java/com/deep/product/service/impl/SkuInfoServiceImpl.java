package com.deep.product.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.deep.product.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.common.utils.BeanUtils;
import com.deep.common.utils.PageUtils;
import com.deep.common.utils.Query;
import com.deep.product.dao.SkuInfoDao;
import com.deep.product.model.entity.SkuImagesEntity;
import com.deep.product.model.entity.SkuInfoEntity;
import com.deep.product.model.entity.SkuSaleAttrValueEntity;
import com.deep.product.model.entity.SpuInfoDescEntity;
import com.deep.product.model.params.SkuImageParam;
import com.deep.product.model.params.SkuParam;
import com.deep.product.model.params.SpuSaveParam;
import com.deep.product.model.vo.SkuItemSaleAttrVO;
import com.deep.product.model.vo.SkuItemVo;
import com.deep.product.model.vo.SpuItemAttrGroupVO;

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
        String key = (String)params.get("key");
        if (StringUtils.hasLength(key)) {
            wrapper.like("sku_name", key).or().like("sku_title", key);
        }
        IPage<SkuInfoEntity> page = this.page(new Query<SkuInfoEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }

    @Override
    public SkuItemVo queryItem(@NonNull Long skuId) {
        Assert.notNull(skuId, "商品id不能为空!");
        SkuItemVo skuItemVO = new SkuItemVo();
        // 1.取sku实体
        SkuInfoEntity infoEntity = getById(skuId);
        skuItemVO.setInfo(infoEntity);
        // 2.sku图片信息
        List<SkuImagesEntity> imagesEntities = skuImagesService.getImagesBySkuId(skuId);
        skuItemVO.setImages(imagesEntities);
        // 3.获取sku销售属性组合
        List<SkuItemSaleAttrVO> saleAttrVos = skuSaleAttrValueService.getSaleAttrsBySpuId(infoEntity.getSpuId());
        skuItemVO.setSaleAttr(saleAttrVos);
        // 4.获取spu介绍
        SpuInfoDescEntity spuDes = spuInfoDescService.getById(infoEntity.getSpuId());
        skuItemVO.setDesc(spuDes);
        // 5.获取规格属性
        List<SpuItemAttrGroupVO> attrGroups =
            attrGroupService.getAttrGroupWithAttrs(infoEntity.getSpuId(), infoEntity.getCatalogId());
        skuItemVO.setGroupAttrs(attrGroups);
        return skuItemVO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void savSkuParams(@NonNull Long spuId, @NonNull SpuSaveParam spuSaveParam) {
        Assert.notNull(spuId, "spuId不能为空!");
        Assert.notNull(spuSaveParam, "商品参数不能为空!");
        spuSaveParam.getSkus().parallelStream().forEach(item -> {
            // 1、保存sku的基本信息 pms_sku_info
            SkuInfoEntity skuEntity = item.convertTo();
            skuEntity.setSpuId(spuId);
            skuEntity.setBrandId(spuSaveParam.getBrandId());
            skuEntity.setCatalogId(spuSaveParam.getCatalogId());
            skuEntity.setSkuDefaultImg(getDefaultImag(item.getImages()));
            skuEntity.setSaleCount((long)(Math.random() * 2870));
            this.save(skuEntity);

            // 2、保存sku的图片信息 pms_sku_images
            List<SkuImagesEntity> images = BeanUtils.transformFromInBatch(item.getImages(), SkuImagesEntity.class);
            images.forEach(img -> img.setSkuId(skuEntity.getSkuId()));
            skuImagesService.saveBatch(images);

            // 3.保存sku的销售属性 pms_sku_sale_attr_value
            List<SkuSaleAttrValueEntity> saleAttrs =
                BeanUtils.transformFromInBatch(item.getAttr(), SkuSaleAttrValueEntity.class);
            saleAttrs.forEach(attr -> attr.setSkuId(skuEntity.getSkuId()));
            skuSaleAttrValueService.saveBatch(saleAttrs);

            // 4、保存sku的优惠信息 gulimall_sms/sms_sku_ladder、sms_sku_full_reduction、sms_member_price
            List<SkuParam.MemberPriceParam> memberPrice = item.getMemberPrice();
        });
    }

    @Override
    public List<String> getSkuSaleAttr(@NonNull Long skuId) {
        Assert.notNull(skuId, "商品id不能为空!");

        List<SkuSaleAttrValueEntity> saleAttrs = skuSaleAttrValueService.getSaleAttrsBySkuId(skuId);
        return saleAttrs.stream().map(item -> item.getAttrName() + ":" + item.getAttrValue())
            .collect(Collectors.toList());
    }

    @Override
    public List<SkuInfoEntity> getBestSale(int count) {
        return this.baseMapper.getBestSale(count);
    }

    /**
     * 获取默认图片
     */
    private String getDefaultImag(List<SkuImageParam> images) {
        Assert.notEmpty(images, "图片集合不能为空!");
        for (SkuImageParam image : images) {
            if (image.getDefaultImg() == 1) {
                return image.getImgUrl();
            }
        }
        return "";
    }
}
