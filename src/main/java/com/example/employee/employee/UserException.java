package com.example.employee.employee;

public class UserException extends RuntimeException{
    private String errorCode;
    public UserException(String errorCode, String errMessage) {
        super(errMessage);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

}
