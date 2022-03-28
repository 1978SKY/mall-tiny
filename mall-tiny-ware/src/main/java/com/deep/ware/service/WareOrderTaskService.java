package com.deep.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deep.common.utils.PageUtils;
import com.deep.ware.model.entity.WareOrderTaskEntity;

import java.util.Map;

/**
 * 库存工作单
 *
 * @author Deep
 * @date 2022/3/28
 */
public interface WareOrderTaskService extends IService<WareOrderTaskEntity> {
    /**
     * 获取采购单列表
     */
    PageUtils queryPage(Map<String, Object> params);
}
