package com.deep.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deep.auth.model.entity.MemberReceiveAddressEntity;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * 会员收货地址
 *
 * @author Deep
 * @date 2022/4/5
 */
public interface MemberReceiveAddressService extends IService<MemberReceiveAddressEntity> {

    /**
     * 获取收货地址
     *
     * @param memberId 会员id
     * @return 收货地址集合
     */
    List<MemberReceiveAddressEntity> getAddresses(@NonNull Long memberId);

}
