package com.deep.seckill.service.impl;

import com.deep.seckill.feign.CouponFeignService;
import com.deep.seckill.model.dto.SeckillSessionWithSkusDTO;
import com.deep.seckill.model.enume.UploadEnums;
import com.deep.seckill.service.UploadService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 上架服务
 *
 * @author Deep
 * @date 2022/4/18
 */
@Service("uploadService")
public class UploadServiceImpl implements UploadService {
    private final CouponFeignService couponFeignService;

    public UploadServiceImpl(CouponFeignService couponFeignService) {
        this.couponFeignService = couponFeignService;
    }

    @Override
    public boolean uploadSeckillSkus() {
        List<SeckillSessionWithSkusDTO> seckillSessions =
                couponFeignService.getSeckillSessions(UploadEnums.THREE_DAY.getDay());

        return false;
    }
}
