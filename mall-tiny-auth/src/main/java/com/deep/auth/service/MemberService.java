package com.deep.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deep.auth.model.entity.MemberEntity;
import com.deep.auth.model.entity.MemberReceiveAddressEntity;
import com.deep.auth.model.params.PasswordParam;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * 会员
 *
 * @author Deep
 * @date 2022/4/1
 */
public interface MemberService extends IService<MemberEntity> {
    /**
     * 修改密码
     *
     * @param param 密码参数
     */
    boolean updatePassword(PasswordParam param);
}
