package com.deep.ware.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.common.utils.PageUtils;
import com.deep.common.utils.Query;
import com.deep.ware.dao.WareInfoDao;
import com.deep.ware.model.entity.WareInfoEntity;
import com.deep.ware.service.WareInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 仓库信息
 *
 * @author Deep
 * @date 2022/3/28
 */
@Service("wareInfoService")
public class WareInfoServiceImpl extends ServiceImpl<WareInfoDao, WareInfoEntity> implements WareInfoService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<WareInfoEntity> wrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (StringUtils.hasLength(key)) {
            wrapper.eq("id", key).or()
                    .eq("areacode", key).or()
                    .like("name", key).or()
                    .like("address", key);
        }
        IPage<WareInfoEntity> page =
                this.page(new Query<WareInfoEntity>().getPage(params), wrapper);

        return new PageUtils(page);
    }
}
