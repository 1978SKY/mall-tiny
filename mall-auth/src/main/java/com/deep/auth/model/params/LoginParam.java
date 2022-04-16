package com.deep.auth.model.params;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 用户登录参数
 *
 * @author Deep
 * @date 2022/3/30
 */
@Data
public class LoginParam {
    @NotEmpty(message = "账号不能为空!")
    private String username;
    @NotEmpty(message = "密码不能为空!")
    private String password;
}
