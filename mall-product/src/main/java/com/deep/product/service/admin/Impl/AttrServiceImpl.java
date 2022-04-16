package com.deep.product.service.admin.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.common.utils.PageUtils;
import com.deep.common.utils.Query;
import com.deep.product.dao.AttrDao;
import com.deep.product.model.entity.AttrAttrgroupRelationEntity;
import com.deep.product.model.entity.AttrEntity;
import com.deep.product.model.enume.AttrEnum;
import com.deep.product.model.params.AttrParam;
import com.deep.product.model.vo.AttrRespVO;
import com.deep.product.model.vo.AttrVO;
import com.deep.product.service.admin.AttrAttrgroupRelationService;
import com.deep.product.service.admin.AttrService;
import com.deep.product.service.admin.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商品属性规格
 *
 * @author Deep
 * @date 2022/3/18
 */
@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AttrAttrgroupRelationService relationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryAttrPage(Map<String, Object> params, Long catId, String type) {
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<>();
        // Attribute type(base or sale)
        int attrType = "base".equalsIgnoreCase(type)
                ? AttrEnum.ATTR_TYPE_BASE.getCode() : AttrEnum.ATTR_TYPE_SALE.getCode();
        wrapper.eq("attr_type", attrType);

        if (catId > 0L) {
            // Get the attributes under the specified category
            wrapper.eq("catelog_id", catId);
        }

        String key = (String) params.get("key");
        if (StringUtils.hasLength(key)) {
            // Get the value by the key
            wrapper.eq("attr_id", key).or().like("attr_name", key);
        }

        // Get page
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params), wrapper);
        PageUtils pageUtils = new PageUtils(page);
        // Reassemble the data
        List<AttrRespVO> respVOS = page.getRecords().stream().map(item -> {
            AttrRespVO attrRespVO = new AttrRespVO();
            BeanUtils.copyProperties(item, attrRespVO);
            String catName = categoryService.getById(item.getCatelogId()).getName();
            String groupName = relationService.getGroupNameByAttrId(item.getAttrId());
            attrRespVO.setCatelogName(catName);
            attrRespVO.setGroupName(groupName);
            return attrRespVO;
        }).collect(Collectors.toList());
        pageUtils.setList(respVOS);

        return pageUtils;
    }

    @Override
    public AttrVO getAttrInfo(Long attrId) {
        Assert.notNull(attrId, "属性id不能为空!");
        AttrVO attrVO = new AttrVO();

        AttrEntity attrEntity = this.getById(attrId);
        BeanUtils.copyProperties(attrEntity, attrVO);
        // Find category path.
        Long[] path = categoryService.findCategoryPath(attrVO.getCatelogId());
        attrVO.setCatelogPath(path);

        return attrVO;
    }

    @Transactional
    @Override
    public void saveAttr(AttrParam attrParam) {
        AttrEntity attrEntity = attrParam.convertTo();
        this.save(attrEntity);
        // 保存关联关系表
        Long groupId = attrParam.getAttrGroupId();
        if (groupId != null) {   // 不为空时为基础属性，需要保存关联关系表
            saveRelation(attrEntity.getAttrId(), groupId);
        }
    }

    @Transactional
    @Override
    public void deleteAttrs(List<Long> ids) {
        removeByIds(ids);
        relationService.removeByAttrIds(ids);
        Assert.notEmpty(ids, "属性id集合不能为空!");
    }

    @Transactional
    @Override
    public void updateAttr(AttrParam attrParam) {
        AttrEntity attrEntity = attrParam.convertTo();
        this.updateById(attrEntity);
        Long groupId = attrParam.getAttrGroupId();
        if (groupId != null) {
            // 更新关联分类(由于前端没有传来原来的属性分组id且，只有属性id无法确定分组关联id。因此只能进行插入后再进行手动删除)
            saveRelation(attrEntity.getAttrId(), groupId);
        }
    }

    /**
     * 保存属性分类关联表
     */
    private void saveRelation(Long attrId, Long groupId) {
        Assert.notNull(attrId, "属性id不能为空!");
        Assert.notNull(groupId, "分组id不能为空!");
        AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
        relationEntity.setAttrId(attrId);
        relationEntity.setAttrGroupId(groupId);
        relationService.save(relationEntity);
    }


}
