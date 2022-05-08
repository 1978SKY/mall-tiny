package com.deep.product.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.common.exception.FeignRequestException;
import com.deep.common.utils.BeanUtils;
import com.deep.common.utils.PageUtils;
import com.deep.common.utils.Query;
import com.deep.common.utils.R;
import com.deep.product.dao.SpuInfoDao;
import com.deep.product.feign.SearchFeignService;
import com.deep.product.feign.WareFeignService;
import com.deep.product.model.dto.SkuEsDTO;
import com.deep.product.model.entity.*;
import com.deep.product.model.params.SpuSaveParam;
import com.deep.product.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * spu信息
 *
 * @author Deep
 * @date 2022/3/20
 */
@Slf4j
@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private SkuInfoService skuInfoService;
    @Autowired
    private SpuInfoDescService spuInfoDescService;
    @Autowired
    private SpuImagesService spuImagesService;
    @Autowired
    private ProductAttrValueService productAttrValueService;
    @Autowired
    private SearchFeignService searchFeignService;
    @Autowired
    private WareFeignService wareFeignService;
    @Autowired
    private ThreadPoolExecutor executor;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveSpuDetail(SpuSaveParam spuSaveParam) {
        // TODO 分布式事务回滚，当1、2、3、4、5、6中的任意一个步骤执行错误时事务回滚

        // 1、保存spu基本信息  pms_spu_info
        SpuInfoEntity spuInfoEntity = BeanUtils.transformFrom(spuSaveParam, SpuInfoEntity.class);
        this.save(spuInfoEntity);
        assert spuInfoEntity != null;
        Long spuId = spuInfoEntity.getId();
        // 2、保存spu的描述图片 pms_spu_info_desc
        CompletableFuture<Void> descFuture = CompletableFuture.runAsync(() -> {
            List<String> decrypt = spuSaveParam.getDecript();
            SpuInfoDescEntity descEntity = new SpuInfoDescEntity();
            descEntity.setSpuId(spuId);
            descEntity.setDecript(String.join(",", decrypt));
            spuInfoDescService.save(descEntity);
        }, executor);
        // 3、保存商品图片集  pms_spu_images
        CompletableFuture<Void> imgFuture = CompletableFuture.runAsync(() -> {
            spuImagesService.saveImages(spuId, spuSaveParam.getImages());
        }, executor);
        // 4、保存spu的规格参数 pms_product_attr_value
        CompletableFuture<Void> attrFuture = CompletableFuture.runAsync(() -> {
            productAttrValueService.saveAttrs(spuId, spuSaveParam.getBaseAttrs());
        }, executor);
        // 5、远程保存spu的积分信息
        CompletableFuture<Void> integralFuture = CompletableFuture.runAsync(() -> {
            SpuSaveParam.BoundsParam bounds = spuSaveParam.getBounds();
        }, executor);
        // 6、保存对应的所有sku信息
        CompletableFuture<Void> skuFuture = CompletableFuture.runAsync(() -> {
            skuInfoService.savSkuParams(spuId, spuSaveParam);
        }, executor);

        CompletableFuture.allOf(descFuture, imgFuture, attrFuture, integralFuture, skuFuture);
    }

    @Override
    public void up(@NonNull Long spuId) {
        Assert.notNull(spuId, "spuId不能为空!");
        // 1、组装数据
        // ①、查出当前spuId对应的所有基础信息、品牌名、品牌logo、分类名、基本属性信息
        SpuInfoEntity spuInfoEntity = this.getById(spuId);
        BrandEntity brandEntity = brandService.getById(spuInfoEntity.getBrandId());
        CategoryEntity catEntity = categoryService.getById(spuInfoEntity.getCatalogId());
        List<SkuEsDTO.BaseAttr> baseAttrs = productAttrValueService.baseAttrsForSpu(spuId)
                .stream().map(item -> new SkuEsDTO.BaseAttr(item.getAttrId(),
                        item.getAttrName(), item.getAttrValue())).collect(Collectors.toList());
        // spu对应的所有sku
        List<SkuInfoEntity> skuInfoEntities = skuInfoService
                .list(new QueryWrapper<SkuInfoEntity>().eq("spu_id", spuId));
        List<Long> skuIds = skuInfoEntities.stream().map(SkuInfoEntity::getSkuId).collect(Collectors.toList());
        // 获取库存
        Map<Long, Boolean> stockMap = hasStock(skuIds);
        List<SkuEsDTO> skuEsDTOList = skuInfoEntities.stream().map(item -> {
            SkuEsDTO skuEsDTO = BeanUtils.transformFrom(item, SkuEsDTO.class);
            assert skuEsDTO != null;
            skuEsDTO.setSkuPrice(item.getPrice());
            skuEsDTO.setSkuImg(item.getSkuDefaultImg());
            skuEsDTO.setBrandName(brandEntity.getName());
            skuEsDTO.setBrandImg(brandEntity.getLogo());
            skuEsDTO.setCatalogName(catEntity.getName());
            skuEsDTO.setAttrs(baseAttrs);
            skuEsDTO.setHasStock(stockMap.get(item.getSkuId()));
            skuEsDTO.setHotScore(100L);
            return skuEsDTO;
        }).collect(Collectors.toList());

        R r = searchFeignService.uploadProduct(skuEsDTOList);
        if (r.getCode() == 0) {
            // 远程调用成功，更改spu发布状态
            UpdateWrapper<SpuInfoEntity> wrapper = new UpdateWrapper<>();
            wrapper.eq("id", spuInfoEntity.getId());
            this.update(wrapper.set("publish_status", 1));
        } else {
            // 远程调用失败
            // TODO 接口幂等性 重试机制
        }
    }

    /**
     * 远程调用库存系统，查询是否有库存
     *
     * @param skuIds 商品id集合
     * @return 是否包含库存的map映射
     */
    private Map<Long, Boolean> hasStock(List<Long> skuIds) {
        Assert.notEmpty(skuIds, "商品id集合不能为空!");

        R r = wareFeignService.isHasStock(skuIds);
        if (r.getCode() != 0) {
            throw new FeignRequestException("仓库系统远程服务调用失败!");
        }
        return r.getData("data", new TypeReference<>() {
        });
    }

}