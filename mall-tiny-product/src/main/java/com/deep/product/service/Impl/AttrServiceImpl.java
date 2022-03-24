package com.deep.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.common.utils.PageUtils;
import com.deep.common.utils.Query;
import com.deep.product.dao.AttrDao;
import com.deep.product.model.entity.AttrEntity;
import com.deep.product.model.enume.AttrEnum;
import com.deep.product.model.vo.AttrRespVO;
import com.deep.product.model.vo.AttrVO;
import com.deep.product.service.AttrAttrgroupRelationService;
import com.deep.product.service.AttrService;
import com.deep.product.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
}
