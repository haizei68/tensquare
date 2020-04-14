package com.tensquare.asset.entity;

import java.io.Serializable;

public class ApiResponse<T> implements Serializable {
    private static final long serialVersionUID = 2546181131801166096L;
    private String resultCode;
    private String resultMsg;
    private String errorCode;
    private T resultObj;
    public static final String SUCCESS="0";
    public static final String VALIDATE_FAIL="1";
    public static final String FAIL = "2";
    public static final String UNKNOWN = "9999";
    public static final String TOKEN_EXPIRED = "9998";
    public static final String AUTH_FAIL = "9997";

    public ApiResponse() {
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public T getResultObj() {
        return resultObj;
    }

    public void setResultObj(T resultObj) {
        this.resultObj = resultObj;
    }
}
