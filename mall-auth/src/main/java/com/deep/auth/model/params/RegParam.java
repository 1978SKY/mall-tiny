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
    private String username;
    private String password;
    private String phone;
}
