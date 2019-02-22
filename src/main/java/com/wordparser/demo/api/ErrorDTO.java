package com.wordparser.demo.api;

public class ErrorDTO {

    private String errorCode;
    private String errorMsg;

    private ErrorDTO() {
    }

    public ErrorDTO(String errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorDTO(String errorCode, String msg) {
        this.errorCode = errorCode;
        this.errorMsg = msg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
