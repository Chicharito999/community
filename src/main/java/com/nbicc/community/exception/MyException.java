package com.nbicc.community.exception;

public class MyException extends RuntimeException {
    private String message;

    public MyException(String message) {
        this.message = message;
    }

    public MyException(ICustomizeErrorCode customizeErrorCode) {
        this.message = customizeErrorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return this.message;
    }


}
