package com.deep.auth.model.params;

import lombok.Data;

/**
 * 修改密码
 *
 * @author Deep
 * @date 2022/4/13
 */
@Data
public class PasswordParam {
    private Long memberId;
    private String oldPassword;
    private String newPassword;
}
