package com.deep.product.service.web.Impl;

import com.deep.product.model.entity.SkuInfoEntity;
import com.deep.product.service.admin.SkuInfoService;
import com.deep.product.service.web.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
