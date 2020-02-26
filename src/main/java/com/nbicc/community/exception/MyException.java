package com.nbicc.community.exception;

public class MyException extends RuntimeException {
    private String message;
    private String code;

    public MyException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public MyException(ICustomizeErrorCode customizeErrorCode) {
        this.message = customizeErrorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return this.code;
    }
}
