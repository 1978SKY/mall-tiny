package com.deep.seckill.feign;

import com.deep.common.utils.R;
import com.deep.seckill.model.dto.SeckillSessionWithSkusDTO;
import com.deep.seckill.model.enume.UploadEnums;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 优惠模块远程调用服务
 *
 * @author Deep
 * @date 2022/4/18
 */
@FeignClient("mall-coupon")
public interface CouponFeignService {
    /**
     * 获取最近秒杀场次
     *
     * @param day 时间
     * @return 秒杀场次
     */
    @GetMapping("/api/coupon/seckillsession/getSkillSessions")
    R getSeckillSessions(@RequestParam(value = "day") int day);
}
