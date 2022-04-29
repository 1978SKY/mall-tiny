package com.deep.product.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.common.utils.BeanUtils;
import com.deep.common.utils.PageUtils;
import com.deep.common.utils.Query;
import com.deep.product.dao.AttrAttrgroupRelationDao;
import com.deep.product.model.entity.AttrAttrgroupRelationEntity;
import com.deep.product.model.entity.AttrEntity;
import com.deep.product.model.enume.AttrEnum;
import com.deep.product.model.params.AttrGroupRelationParam;
import com.deep.product.model.vo.AttrVO;
import com.deep.product.service.AttrAttrgroupRelationService;
import com.deep.product.service.AttrGroupService;
import com.deep.product.service.AttrService;

/**
 * 属性&属性分组关联
 *
 * @author Deep
 * @date 2022/3/18
 */
@Service("attrAttrgroupRelationService")
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationDao, AttrAttrgroupRelationEntity>
    implements AttrAttrgroupRelationService {

    @Autowired
    private AttrService attrService;
    @Autowired
    private AttrGroupService attrGroupService;
    @Autowired
    private AttrAttrgroupRelationDao relationDao;

    @Override
    public String getGroupNameByAttrId(Long attrId) {
        Assert.notNull(attrId, "属性id不能为空!");
        return relationDao.getGroupNameByAttrId(attrId);
    }

    @Override
    public List<AttrVO> getAttrsByGroupId(Long groupId) {
        Assert.notNull(groupId, "分组id不能为空!");

        return relationDao.getAttrByGroupId(groupId);
    }

    @Override
    public PageUtils getNoRelationAttrPage(Map<String, Object> params, Long groupId) {
        Assert.notNull(groupId, "分组id不能为空!");
        Long catId = attrGroupService.getById(groupId).getCatelogId();
        if (catId == null) {
            throw new IllegalArgumentException("分组id异常!");
        }
        // 封装查询器
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("catelog_id", catId).eq("attr_type", AttrEnum.ATTR_TYPE_BASE.getCode());
        String key = (String)params.get("key");
        if (StringUtils.hasLength(key)) {
            wrapper.like("attr_name", key).or().like("value_select", key);
        }
        IPage<AttrEntity> page = attrService.page(new Query<AttrEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }

    @Override
    public void saveRelation(List<AttrGroupRelationParam> params) {
        List<AttrAttrgroupRelationEntity> entityList =
            BeanUtils.transformFromInBatch(params, AttrAttrgroupRelationEntity.class);
        this.saveBatch(entityList);
    }

    @Override
    public void deleteRelations(List<AttrGroupRelationParam> params) {
        relationDao.deleteRelations(params);
    }

    @Override
    public void removeByAttrIds(List<Long> attrIds) {
        Assert.notEmpty(attrIds, "属性id集合不能为空!");

        relationDao.deleteByAttrIds(attrIds);
    }
}
