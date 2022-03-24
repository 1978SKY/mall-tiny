package com.deep.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.common.utils.BeanUtils;
import com.deep.common.utils.PageUtils;
import com.deep.common.utils.Query;
import com.deep.product.dao.AttrGroupDao;
import com.deep.product.model.entity.AttrAttrgroupRelationEntity;
import com.deep.product.model.entity.AttrGroupEntity;
import com.deep.product.model.vo.AttrGroupWithAttrsVO;
import com.deep.product.model.vo.AttrVO;
import com.deep.product.service.AttrAttrgroupRelationService;
import com.deep.product.service.AttrGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 属性分组
 *
 * @author Deep
 * @date 2022/3/17
 */
@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Autowired
    private AttrAttrgroupRelationService relationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long categoryId) {
        Assert.notNull(categoryId, "分类路径不能为空!");

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

    @Transactional
    @Override
    public void deleteBatch(List<Long> ids) {
        Assert.notNull(ids, "属性分组id不能为空!");
        this.removeByIds(ids);
        // 删除关联表
        QueryWrapper<AttrAttrgroupRelationEntity> wrapper = new QueryWrapper<>();
        ids.forEach(item -> wrapper.eq("attr_group_id", item).or());

        relationService.remove(wrapper);
    }

    @Override
    public List<AttrGroupWithAttrsVO> getAttrGroupWithAttrs(Long catId) {
        Assert.notNull(catId, "catId不能为空!");
        // 两次查表
        List<AttrGroupEntity> groups =
                this.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catId));
        List<AttrGroupWithAttrsVO> groupWithAttrsVOS =
                BeanUtils.transformFromInBatch(groups, AttrGroupWithAttrsVO.class);

        groupWithAttrsVOS.forEach(item -> {
            List<AttrGroupWithAttrsVO.AttrVO> attrs = relationService.getAttrsByGroupId(item.getAttrGroupId()).stream().map(attr
                    -> new AttrGroupWithAttrsVO.AttrVO(attr.getAttrId(), attr.getAttrName())).collect(Collectors.toList());
            item.setAttrs(attrs);
        });
        return groupWithAttrsVOS;
    }

}
