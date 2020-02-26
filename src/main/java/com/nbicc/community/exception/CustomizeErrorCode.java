package com.nbicc.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    NOTEXIST("201","您查找的内容不存在"),
    NOTLOGIN("202","用户未登录"),
    SYS_ERROR("203","系统异常");
    private String message;
    private String code;

    CustomizeErrorCode(String code, String message) {
        this.code =code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public String getCode() {
        return this.code;
    }
}
