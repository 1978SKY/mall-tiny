package com.deep.seckill.service;

import com.deep.seckill.model.enume.UploadEnums;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 秒杀商品上架
 *
 * @author Deep
 * @date 2022/6/8
 */
@SpringBootTest
class UploadServiceTest {

    @Autowired
    private UploadService uploadService;

    /**
     * 秒杀商品上架
     */
    @Test
    void uploadSeckillSkus() {

        boolean b = uploadService.uploadSeckillSkus(UploadEnums.THREE_DAY.getDay());
        System.out.println(b ? "success" : "fail");

    }
}