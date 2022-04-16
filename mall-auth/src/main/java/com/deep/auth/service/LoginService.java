package com.deep.auth.service;

import com.deep.auth.model.entity.MemberEntity;
import com.deep.auth.model.params.LoginParam;
import com.deep.auth.model.params.SocialParam;

/**
 * 登录
 *
 * @author Deep
 * @date 2022/3/31
 */
public interface LoginService {
    /**
     * 登录
     */
    MemberEntity login(LoginParam loginParam);

    /**
     * 社交登录（第一次登录将进行注册操作）
     */
    MemberEntity oauthLogin(SocialParam social) throws Exception;
}
