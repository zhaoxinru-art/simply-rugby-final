package com.sqa.simplyrugby.common;

import lombok.Getter;

/**
 * 响应状态码枚举
 */
@Getter
public enum ResultCode {
    // 通用
    SUCCESS(0, "success"),
    ERROR(1, "系统异常"),

    // 用户相关
    USER_HAS_EXISTED(1001, "用户名已存在"),
    USER_LOGIN_ERROR(1002, "用户名或密码错误"),
    USER_NOT_EXIST(1003, "用户不存在"),
    USER_NOT_LOGIN(1004, "用户未登录"),
    USER_PERMISSION_ERROR(1005, "权限不足");

    private final Integer code;
    private final String msg;

    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}