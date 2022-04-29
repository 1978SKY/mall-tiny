package com.deep.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.product.dao.SpuInfoDescDao;
import com.deep.product.model.entity.SpuInfoDescEntity;
import com.deep.product.service.SpuInfoDescService;
import org.springframework.stereotype.Service;

/**
 * spu信息介绍
 *
 * @author Deep
 * @date 2022/3/24
 */
@Service("spuInfoDescService")
public class SpuInfoDescServiceImpl extends ServiceImpl<SpuInfoDescDao, SpuInfoDescEntity> implements SpuInfoDescService {
}
