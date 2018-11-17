package com.crcc.common.exception;

public enum ResponseCode {

    OK("200","success"),
    //300-399 订单错误
    NOT_FOUND_LOGISTICS_DETAILS("301","not found logistics traces"),
    //400-500
    AUTH_FAILED("401","User authentication failed"),
    PERMISSION_CHANGED("402","permission changed"),
    PARAM_ILLEGAL("420","param is illegal"),
    NOT_FOUND_USER("421","not found this user"),
    PASSWORD_ERROR("422","password is error"),
    //500-600
    SERVER_ERROR("500","server error");

    private String code;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    ResponseCode(String code, String message){
        this.code = code;
        this.message = message;
    }
}
