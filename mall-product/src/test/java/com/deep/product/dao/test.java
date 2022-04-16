package com.deep.product.dao;

import com.deep.product.model.entity.BrandEntity;
import com.deep.product.model.params.BrandParam;

/**
 * @author Deep
 * @date 2022/3/15
 */
public class test {
    public static void main(String[] args) {
        BrandParam param = new BrandParam();
        param.setBrandId(1L);
        param.setName("HAU");
        long start = System.currentTimeMillis();
        BrandEntity entity = param.convertTo();     // 343ms
        long end = System.currentTimeMillis();
        System.out.println((end - start) + "ms");
        System.out.println(entity);
    }


}
