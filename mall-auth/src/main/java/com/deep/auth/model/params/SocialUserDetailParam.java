package com.deep.auth.model.params;

import lombok.Data;

import java.util.Date;

/**
 * Gitee社交登录用户详细信息
 *
 * @author Deep
 * @date 2022/4/2
 */
@Data
public class SocialUserDetailParam {
    private Long id;
    private String login;
    private String name;
    private String avatar_url;
    private String url;
    private String html_url;
    private String remark;
    private String followers_url;
    private String following_url;
    private String gists_url;
    private String starred_url;
    private String subscriptions_url;
    private String organizations_url;
    private String repos_url;
    private String events_url;
    private String received_events_url;
    private String type;
    private String blog;
    private String weibo;
    private String bio;
    private int public_repos;
    private int public_gists;
    private int followers;
    private int following;
    private int stared;
    private int watched;
    private Date created_at;
    private Date updated_at;
    private String email;
}
