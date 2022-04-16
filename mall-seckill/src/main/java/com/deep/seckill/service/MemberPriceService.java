package com.deep.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deep.common.utils.PageUtils;
import com.deep.seckill.model.entity.MemberPriceEntity;

import java.util.Map;

/**
 * 商品会员价格
 *
 * @author Deep
 * @date 2022/4/16
 */
public interface MemberPriceService extends IService<MemberPriceEntity> {
    /**
     * 获取商品会员价格
     *
     * @param params 查询参数
     * @return 商品会员价格列表
     */
    PageUtils queryPage(Map<String, Object> params);
}
