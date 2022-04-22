package com.deep.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.common.utils.BeanUtils;
import com.deep.common.utils.DateUtil;
import com.deep.common.utils.PageUtils;
import com.deep.common.utils.Query;
import com.deep.coupon.dao.SeckillSessionDao;
import com.deep.coupon.model.entity.SeckillSessionEntity;
import com.deep.coupon.model.entity.SeckillSkuRelationEntity;
import com.deep.coupon.model.vo.SeckillSessionWithSkusVO;
import com.deep.coupon.model.vo.SeckillSkuVO;
import com.deep.coupon.service.SeckillSessionService;
import com.deep.coupon.service.SeckillSkuRelationService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 每日秒杀
 *
 * @author Deep
 * @date 2022/4/16
 */
@Service("seckillSessionService")
public class SeckillSessionServiceImpl extends ServiceImpl<SeckillSessionDao, SeckillSessionEntity>
    implements SeckillSessionService {

    private final SeckillSkuRelationService relationService;

    public SeckillSessionServiceImpl(SeckillSkuRelationService relationService) {
        this.relationService = relationService;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<SeckillSessionEntity> wrapper = new QueryWrapper<>();
        String key = (String)params.get("key");
        if (StringUtils.hasLength(key)) {
            wrapper.eq("id", key).or().like("name", key);
        }
        IPage<SeckillSessionEntity> iPage = this.page(new Query<SeckillSessionEntity>().getPage(params), wrapper);
        return new PageUtils(iPage);
    }

    @Override
    public List<SeckillSessionWithSkusVO> querySeckillSessions(int day) {
        // 获取秒杀场次
        List<SeckillSessionEntity> sessions = list(new QueryWrapper<SeckillSessionEntity>().eq("status", 1)
            .between("start_time", DateUtil.getStartTime(), DateUtil.getEndTime(day)));
        if (sessions == null || sessions.size() < 1) {
            return null;
        }
        // 获取秒杀场次下的秒杀商品
        List<Long> sessionIds = sessions.stream().map(SeckillSessionEntity::getId).collect(Collectors.toList());
        Map<Long, List<SeckillSkuRelationEntity>> sessionSkusMap = relationService.getBySessionIds(sessionIds);
        // 组装并返回
        return sessions.stream().map(item -> {
            SeckillSessionWithSkusVO sessionVO = BeanUtils.transformFrom(item, SeckillSessionWithSkusVO.class);
            List<SeckillSkuRelationEntity> subSkus = sessionSkusMap.get(item.getId());
            if (!CollectionUtils.isEmpty(subSkus)) {
                List<SeckillSkuVO> skuVos = BeanUtils.transformFromInBatch(subSkus, SeckillSkuVO.class);
                Objects.requireNonNull(sessionVO).setSeckillSkus(skuVos);
            }
            return sessionVO;
        }).collect(Collectors.toList());
    }
}
