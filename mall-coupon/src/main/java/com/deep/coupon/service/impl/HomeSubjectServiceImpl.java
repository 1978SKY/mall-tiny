package com.deep.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.common.utils.PageUtils;
import com.deep.common.utils.Query;
import com.deep.coupon.dao.HomeSubjectDao;
import com.deep.coupon.model.entity.HomeSubjectEntity;
import com.deep.coupon.service.HomeSubjectService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 首页专题表
 *
 * @author Deep
 * @date 2022/4/16
 */
@Service("homeSubjectService")
public class HomeSubjectServiceImpl
        extends ServiceImpl<HomeSubjectDao, HomeSubjectEntity> implements HomeSubjectService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<HomeSubjectEntity> wrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (StringUtils.hasLength(key)) {
            wrapper.eq("id", key).or().like("name", key);
        }
        IPage<HomeSubjectEntity> page = this.page(new Query<HomeSubjectEntity>().getPage(params), wrapper);

        return new PageUtils(page);
    }
}
