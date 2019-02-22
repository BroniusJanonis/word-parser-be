package com.wordparser.demo.validator;

public abstract class ValidationError {

    private String errorCode = "VALIDATION_ERROR";
    private String errorMsg;


    public ValidationError(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public ValidationError(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValidationError that = (ValidationError) o;

        return errorCode.equals(that.errorCode);

    }

    @Override
    public int hashCode() {
        return errorCode.hashCode();
    }

    @Override
    public String toString() {
        return "ValidationError{" +
                "errorCode='" + errorCode + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
