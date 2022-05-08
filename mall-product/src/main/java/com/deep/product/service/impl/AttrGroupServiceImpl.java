package com.deep.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.common.utils.BeanUtils;
import com.deep.common.utils.PageUtils;
import com.deep.common.utils.Query;
import com.deep.product.dao.AttrGroupDao;
import com.deep.product.model.entity.AttrAttrgroupRelationEntity;
import com.deep.product.model.entity.AttrEntity;
import com.deep.product.model.entity.AttrGroupEntity;
import com.deep.product.model.vo.AttrGroupWithAttrsVO;
import com.deep.product.model.vo.AttrVO;
import com.deep.product.model.vo.SpuItemAttrGroupVO;
import com.deep.product.service.AttrAttrgroupRelationService;
import com.deep.product.service.AttrGroupService;
import com.deep.product.service.AttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
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
    private AttrGroupDao attrGroupDao;

    @Autowired
    private AttrService attrService;

    @Autowired
    private AttrAttrgroupRelationService relationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catId) {
        Assert.notNull(catId, "分类id不能为空!");

        QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (StringUtils.hasLength(key)) {
            wrapper.and((obj) ->
                    obj.eq("attr_group_id", key).or().like("attr_group_name", key)
            );
        }
        if (catId > 0) {
            wrapper.eq("catelog_id", catId);
        }
        IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params),
                wrapper);
        return new PageUtils(page);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatch(@NonNull List<Long> ids) {
        Assert.notEmpty(ids, "属性分组id不能为空!");
        this.removeByIds(ids);
        // 删除关联表
        QueryWrapper<AttrAttrgroupRelationEntity> wrapper = new QueryWrapper<>();
        ids.forEach(item -> wrapper.eq("attr_group_id", item).or());

        relationService.remove(wrapper);
    }

    @Override
    public List<AttrGroupWithAttrsVO> getAttrGroupWithAttrs(@NonNull Long catId) {
        Assert.notNull(catId, "catId不能为空!");
        // 1.查询当前分类下的所有属性分组
        List<AttrGroupWithAttrsVO> attrGroupWithAttrsVos =
                list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catId))
                        .stream()
                        .map(item -> BeanUtils.transformFrom(item, AttrGroupWithAttrsVO.class))
                        .collect(Collectors.toList());

        // 2.查询每个分组的所有信息
        QueryWrapper<AttrAttrgroupRelationEntity> wrapper = new QueryWrapper<>();
        for (AttrGroupWithAttrsVO groupVo : attrGroupWithAttrsVos) {
            wrapper.clear();
            wrapper.eq("attr_group_id", groupVo.getAttrGroupId());
            List<AttrAttrgroupRelationEntity> relations = relationService.list(wrapper);
            List<Long> attrIds = relations.stream()
                    .map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());
            if (attrIds.size() > 0) {
                List<AttrEntity> attrs = attrService.listByIds(attrIds);
                groupVo.setAttrs(attrs);
            }
        }
        return attrGroupWithAttrsVos;
    }

    @Override
    public List<SpuItemAttrGroupVO> getAttrGroupWithAttrs(@NonNull Long spuId, @NonNull Long catId) {
        Assert.notNull(spuId, "spuId不能为空!");
        Assert.notNull(catId, "catId不能为空!");

        return attrGroupDao.getAttrGroupWithAttrs(spuId, catId);
    }

}
