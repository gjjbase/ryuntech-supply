package com.ryuntech.common.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author antu
 * @date 2019-05-22
 */
@Getter
@AllArgsConstructor
public enum CommonEnums {

    LOGIN_ERROR(100, "用户名或密码错误"),
    PARAM_ERROR(101, "参数错误"),
    USER_ERROR(102, "获取用户信息失败"),
    LOGOUT_ERROR(103, "退出失败"),
    SYSTEM_ERROR(104, "系统内部错误");

    private final int code;
    private final String msg;

}
