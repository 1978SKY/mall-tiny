package com.deep.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deep.auth.dao.MemberReceiveAddressDao;
import com.deep.auth.model.entity.MemberReceiveAddressEntity;
import com.deep.auth.service.MemberReceiveAddressService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 会员收货地址
 *
 * @author Deep
 * @date 2022/4/5
 */
@Service("memberReceiveAddressService")
public class MemberReceiveAddressServiceImpl extends ServiceImpl<MemberReceiveAddressDao, MemberReceiveAddressEntity>
        implements MemberReceiveAddressService {
    @Override
    public List<MemberReceiveAddressEntity> getAddresses(@NonNull Long memberId) {
        Assert.notNull(memberId, "会员id不能为空!");

        QueryWrapper<MemberReceiveAddressEntity> wrapper = new QueryWrapper<>();
        return list(wrapper.eq("member_id", memberId));
    }

}
