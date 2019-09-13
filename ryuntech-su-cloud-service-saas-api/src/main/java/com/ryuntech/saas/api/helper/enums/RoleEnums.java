package com.ryuntech.saas.api.helper.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnums {
    LOGIN_ERROR(100, "用户名或密码错误"),
    PARAM_ERROR(101, "参数错误");

    private final int code;
    private final String msg;
}
