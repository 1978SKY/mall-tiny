package com.deep.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deep.common.utils.PageUtils;
import com.deep.seckill.model.entity.HomeSubjectEntity;

import java.util.Map;

/**
 * 首页专题表
 *
 * @author Deep
 * @date 2022/4/16
 */
public interface HomeSubjectService extends IService<HomeSubjectEntity> {
    /**
     * 获取首页专题表
     *
     * @param params 查询参数
     * @return 专题表
     */
    PageUtils queryPage(Map<String, Object> params);
}
