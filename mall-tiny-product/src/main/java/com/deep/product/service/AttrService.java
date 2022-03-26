package com.deep.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deep.common.utils.PageUtils;
import com.deep.product.model.entity.AttrEntity;
import com.deep.product.model.vo.AttrVO;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Map;

/**
 * 商品属性规格
 *
 * @author Deep
 * @date 2022/3/18
 */
public interface AttrService extends IService<AttrEntity> {
    /**
     * @param params 查询参数
     * @return 分页
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询属性规格
     *
     * @param params 查询参数
     * @param catId  分类id
     * @param type   属性类型（基础属性/销售属性）
     * @return 分页
     */
    PageUtils queryAttrPage(Map<String, Object> params, Long catId, String type);

    /**
     * 获取指定属性详情
     *
     * @param attrId 属性id
     * @return 属性
     */
    AttrVO getAttrInfo(@NonNull Long attrId);

}
