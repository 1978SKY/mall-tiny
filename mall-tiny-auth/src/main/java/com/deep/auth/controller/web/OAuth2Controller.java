package com.deep.auth.controller.web;

import com.alibaba.fastjson.JSON;
import com.deep.common.model.constant.AuthConstant;
import com.deep.auth.model.entity.MemberEntity;
import com.deep.auth.model.params.SocialParam;
import com.deep.auth.service.LoginService;
import com.deep.common.model.dto.MemberDTO;
import com.deep.common.utils.BeanUtils;
import com.deep.common.utils.HttpUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 第三方登录
 *
 * @author Deep
 * @date 2022/4/2
 */
@Slf4j
@Api("第三方登录")
@Controller
@RequestMapping("/api/auth/oauth2.0")
public class OAuth2Controller {

    @Autowired
    private LoginService loginService;

    /**
     * Gitee登录
     *
     * @param code 信息码（用来换取token）
     */
    @GetMapping("/gitee/doLogin")
    public String gitee(@RequestParam("code") String code, HttpSession session) throws Exception {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("grant_type", "authorization_code");
        paramMap.put("code", code);
        paramMap.put("client_id", "672b6b1a905b8d2395e045349531e5384bfe81423359460142e9d2f388573795");
        paramMap.put("redirect_uri", "http://localhost:8000/api/auth/oauth2.0/gitee/doLogin");
        paramMap.put("client_secret", "f3fae5d8c055d58c192b05112ff75f9e0c3f957b3c6f524461bc9cc581367748");

        // 1、根据code换取access_token
        HttpResponse response =
                HttpUtils.doPost("https://gitee.com", "/oauth/token", "post", new HashMap<>(), paramMap, new HashMap<>());

        // 2、处理response
        if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
            String json = EntityUtils.toString(response.getEntity());
            SocialParam social = JSON.parseObject(json, SocialParam.class);
            log.info("Gitee换取的token值:{}", social.getRefresh_token());
            MemberEntity member = loginService.oauthLogin(social);
            if (member != null) {
                MemberDTO memberDTO = BeanUtils.transformFrom(member, MemberDTO.class);
                session.setAttribute(AuthConstant.LOGIN_USER, memberDTO);
                log.info("登录成功:{}", memberDTO);
                return "redirect:http://localhost:88/api/product/";
            }
        }
        // Login error
        return "redirect:http://localhost:8000/api/auth/login";
    }
}
