package com.deep.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deep.common.utils.PageUtils;
import com.deep.ware.model.entity.WareInfoEntity;

import java.util.Map;

/**
 * 仓库信息
 *
 * @author Deep
 * @date 2022/3/28
 */
public interface WareInfoService extends IService<WareInfoEntity> {
    /**
     * 获取所有仓库
     */
    PageUtils queryPage(Map<String, Object> params);
}
