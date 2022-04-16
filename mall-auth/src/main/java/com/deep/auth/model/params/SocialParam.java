package com.deep.auth.model.params;

import lombok.Data;

/**
 * 社交登录
 *
 * @author Deep
 * @date 2022/4/2
 */
@Data
public class SocialParam {
    /**
     * token
     */
    private String access_token;
    /**
     * token类型
     */
    private String token_type;
    /**
     * 有效时间
     */
    private long expires_in;
    /**
     * 刷新token
     */
    private String refresh_token;
    /**
     * 范围
     */
    private String scope;
    /**
     * 生成时间
     */
    private String created_at;

}
