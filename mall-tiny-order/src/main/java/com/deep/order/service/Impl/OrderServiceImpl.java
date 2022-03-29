package com.deep.order.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.common.utils.PageUtils;
import com.deep.common.utils.Query;
import com.deep.order.dao.OrderDao;
import com.deep.order.model.entity.OrderEntity;
import com.deep.order.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 订单
 *
 * @author Deep
 * @date 2022/3/29
 */
@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<OrderEntity> wrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (StringUtils.hasLength(key)) {
            wrapper.eq("order_sn", key).or()
                    .eq("member_id", key).or()
                    .like("member_username", key);
        }
        IPage<OrderEntity> page =
                this.page(new Query<OrderEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }
}
