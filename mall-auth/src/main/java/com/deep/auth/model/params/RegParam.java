package com.deep.auth.model.params;

import lombok.Data;

/**
 * 注册参数
 *
 * @author Deep
 * @date 2022/3/31
 */
@Data
public class RegParam {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 手机号
     */
    private String phone;
}
