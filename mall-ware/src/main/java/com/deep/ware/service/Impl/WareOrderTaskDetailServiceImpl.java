package com.deep.ware.service.Impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.ware.dao.WareOrderTaskDetailDao;
import com.deep.ware.model.entity.WareOrderTaskDetailEntity;
import com.deep.ware.service.WareOrderTaskDetailService;

/**
 * 库存工作单
 * 
 * @author Deep
 * @date 2022/4/29
 */
@Service("wareOrderTaskDetailService")
public class WareOrderTaskDetailServiceImpl extends ServiceImpl<WareOrderTaskDetailDao, WareOrderTaskDetailEntity>
    implements WareOrderTaskDetailService {

}
