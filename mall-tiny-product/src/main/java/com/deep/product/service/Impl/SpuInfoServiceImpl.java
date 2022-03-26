package com.deep.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.common.utils.BeanUtils;
import com.deep.common.utils.DateUtil;
import com.deep.common.utils.PageUtils;
import com.deep.common.utils.Query;
import com.deep.product.dao.SpuInfoDao;
import com.deep.product.model.entity.SpuImagesEntity;
import com.deep.product.model.entity.SpuInfoDescEntity;
import com.deep.product.model.entity.SpuInfoEntity;
import com.deep.product.model.params.SkuParam;
import com.deep.product.model.params.SpuSaveParam;
import com.deep.product.service.ProductAttrValueService;
import com.deep.product.service.SpuImagesService;
import com.deep.product.service.SpuInfoDescService;
import com.deep.product.service.SpuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * spu信息
 *
 * @author Deep
 * @date 2022/3/20
 */
@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {
    @Autowired
    private SpuInfoDescService spuInfoDescService;
    @Autowired
    private SpuImagesService spuImagesService;
    @Autowired
    private ProductAttrValueService productAttrValueService;

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

    @Transactional
    @Override
    public void saveSpuDetail(SpuSaveParam spuSaveParam) {
        // 1、保存spu基本信息  pms_spu_info
        SpuInfoEntity spuInfoEntity = BeanUtils.transformFrom(spuSaveParam, SpuInfoEntity.class);
        assert spuInfoEntity != null;
        this.save(spuInfoEntity);

        // 2、保存spu的描述图片 pms_spu_info_desc
        List<String> decript = spuSaveParam.getDecript();
        SpuInfoDescEntity descEntity = new SpuInfoDescEntity();
        descEntity.setSpuId(spuInfoEntity.getId());
        descEntity.setDecript(String.join(",", decript));
        spuInfoDescService.save(descEntity);

        // 3、保存商品图片集  pms_spu_images
        spuImagesService.saveImages(spuInfoEntity.getId(), spuSaveParam.getImages());

        // 4、保存spu的规格参数 pms_product_attr_value
        productAttrValueService.saveAttrs(spuInfoEntity.getId(), spuSaveParam.getBaseAttrs());

        // 5、远程保存spu的积分信息
        SpuSaveParam.BoundsParam bounds = spuSaveParam.getBounds();

        // 6、保存对应的所有sku信息
        List<SkuParam> skus = spuSaveParam.getSkus();


    }
}