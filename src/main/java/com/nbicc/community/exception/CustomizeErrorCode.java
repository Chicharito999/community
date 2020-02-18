package com.nbicc.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    NOTEXIST("您查找的问题不存在");
    private String message;

    CustomizeErrorCode(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
