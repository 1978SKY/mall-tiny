package com.deep.auth.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deep.auth.model.entity.MemberEntity;
import com.deep.auth.model.params.LoginParam;
import com.deep.auth.model.params.SocialParam;
import com.deep.auth.model.params.SocialUserDetailParam;
import com.deep.auth.service.LoginService;
import com.deep.auth.service.MemberService;
import com.deep.common.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.HashMap;

/**
 * 登录
 *
 * @author Deep
 * @date 2022/3/31
 */
@Slf4j
@Service("loginService")
public class LoginServiceImpl implements LoginService {

    @Autowired
    private MemberService memberService;

    @Override
    public MemberEntity login(LoginParam loginParam) {
        QueryWrapper<MemberEntity> wrapper = new QueryWrapper<>();
        MemberEntity member =
                memberService.getOne(wrapper.eq("username", loginParam.getUsername()));
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (member != null && encoder.matches(loginParam.getPassword(), member.getPassword())) {
            return member;
        }
        return null;
    }

    @Override
    public MemberEntity oauthLogin(SocialParam social) throws Exception {
        Assert.notNull(social, "社交登录参数不能为空!");

        // 获取uid
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("access_token", social.getAccess_token());
        // 社交登录唯一标识
        SocialUserDetailParam socialUser = null;
        String socialUid = null;
        try {
            HttpResponse response = HttpUtils.doGet("https://gitee.com", "/api/v5/user", "get", new HashMap<>(), paramMap);
            if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
                String json = EntityUtils.toString(response.getEntity());
                socialUser = JSON.parseObject(json, SocialUserDetailParam.class);
                socialUid = socialUser.getId().toString();
            }
        } catch (Exception e) {
            log.error("远程获取Gitee API错误!");
            return null;
        }
        // 具有登录和注册逻辑
        QueryWrapper<MemberEntity> wrapper = new QueryWrapper<>();
        if (StringUtils.hasLength(socialUid)) {
            wrapper.clear();
            MemberEntity member = memberService.getOne(wrapper.eq("social_uid", socialUid));
            if (member != null) {
                // 已经注册过该系统
                return member;
            }
        }
        if (StringUtils.hasLength(socialUser.getLogin())) {
            wrapper.clear();
            MemberEntity member = memberService.getOne(wrapper.eq("username", socialUser.getLogin()));
            if (member != null) {
                // 已经注册过但未绑定social uid
                member.setSocialUid(socialUid);
                memberService.updateById(member);
                return member;
            }
        }
        // 注册当前social uid到当前系统
        MemberEntity member = new MemberEntity();
        member.setSocialUid(socialUid);
        member.setLevelId(1L);
        member.setNickname(socialUser.getName());
        memberService.save(member);
        return member;
    }
}
