package com.deep.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.common.utils.PageUtils;
import com.deep.common.utils.Query;
import com.deep.coupon.dao.MemberPriceDao;
import com.deep.coupon.model.entity.MemberPriceEntity;
import com.deep.coupon.service.MemberPriceService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 商品会员价格
 *
 * @author Deep
 * @date 2022/4/16
 */
@Service("memberPriceService")
public class MemberPriceServiceImpl
        extends ServiceImpl<MemberPriceDao, MemberPriceEntity> implements MemberPriceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<MemberPriceEntity> wrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (StringUtils.hasLength(key)) {
            wrapper.eq("id", key).or()
                    .eq("sku_id", key).or()
                    .eq("member_level_id", key).or()
                    .like("member_level_name", key);
        }
        IPage<MemberPriceEntity> page = this.page(new Query<MemberPriceEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }
}
