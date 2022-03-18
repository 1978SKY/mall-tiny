package com.deep.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deep.common.utils.PageUtils;
import com.deep.product.model.entity.AttrGroupEntity;
import org.springframework.lang.NonNull;

import java.util.Map;

/**
 * 属性分组
 *
 * @author Deep
 * @date 2022/3/17
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    /**
     * 分页查询
     *
     * @param params     分页参数
     * @param categoryId 分类id
     * @return  分页数据
     */
    PageUtils queryPage(Map<String, Object> params,@NonNull Long categoryId);
}
