package com.deep.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.common.utils.PageUtils;
import com.deep.common.utils.Query;
import com.deep.product.dao.AttrGroupDao;
import com.deep.product.model.entity.AttrGroupEntity;
import com.deep.product.service.AttrGroupService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 属性分组
 *
 * @author Deep
 * @date 2022/3/17
 */
@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long categoryId) {
        Assert.notNull(categoryId,"分类路径不能为空!");

        QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<>();

        String key = (String) params.get("key");
        if (StringUtils.hasLength(key)) {
            wrapper.and((obj) ->
                    obj.eq("attr_group_id", key).or().like("attr_group_name", key)
            );
        }
        if (categoryId > 0) {
            wrapper.eq("catelog_id", categoryId);
        }

        IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params),
                wrapper);
        return new PageUtils(page);
    }
}
