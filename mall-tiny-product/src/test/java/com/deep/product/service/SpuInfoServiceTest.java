package com.deep.product.service;

import com.deep.product.model.params.SpuSaveParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * spu信息
 *
 * @author Deep
 * @date 2022/3/25
 */
@SpringBootTest
class SpuInfoServiceTest {
    @Autowired
    private SpuInfoService skuInfoService;

    @Test
    void saveSpuDetail() {
        SpuSaveParam spuSaveParam = new SpuSaveParam();
//        entity.setSpuName("xxx");
        spuSaveParam.setSpuName("xxx");
        skuInfoService.saveSpuDetail(spuSaveParam);
    }

}