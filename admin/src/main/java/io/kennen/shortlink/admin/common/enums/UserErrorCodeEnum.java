package io.kennen.shortlink.admin.common.enums;

import io.kennen.shortlink.admin.common.convention.errorcode.IErrorCode;

public enum UserErrorCodeEnum implements IErrorCode {

    USER_NULL("B000200", "用户记录不存在"),
    USER_NAME_EXIST("B000201", "用户名已存在"),
    USER_ALREADY_EXIST("B000202", "用户记录已存在"),
    USER_NAME_PENDING("B000203", "用户名暂不可用"),
    USER_SAVE_ERROR("B000204", "用户记录新增失败"),
    USER_LOGIN_ERROR("B000205", "用户名或密码错误"),
    USER_ALREADY_LOGGED("B000206", "用户已登录"),
    ;

    private final String code;

    private final String message;

    UserErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
