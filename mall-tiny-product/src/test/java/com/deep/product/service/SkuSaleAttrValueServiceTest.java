package com.deep.product.service;

import com.deep.product.model.vo.SkuItemSaleAttrVO;
import com.deep.product.service.admin.SkuSaleAttrValueService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author Deep
 * @date 2022/3/24
 */
@SpringBootTest
class SkuSaleAttrValueServiceTest {
    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;

    @Test
    void getSaleAttrsBySpuId() {
        List<SkuItemSaleAttrVO> saleAttrsBySpuId = skuSaleAttrValueService.getSaleAttrsBySpuId(17L);
        System.out.println(saleAttrsBySpuId);
    }
}