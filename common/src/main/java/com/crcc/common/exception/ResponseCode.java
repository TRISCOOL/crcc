package com.crcc.common.exception;

public enum ResponseCode {

    OK("200","success"),
    //400-500
    AUTH_FAILED("401","User authentication failed"),
    PERMISSION_CHANGED("402","permission changed"),
    USER_DISABLE("403","this use is disabled"),
    PARAM_ILLEGAL("420","param is illegal"),
    NOT_FOUND_USER("421","not found this user"),
    PASSWORD_ERROR("422","password is error"),
    USER_IS_ALREADY_LOGIN("423","user is already login"),
    //500-600
    SERVER_ERROR("500","server error"),
    PROJECT_INFO_EXIST("600","this project had projectInfo"),
    SUBCONTRACTOR_ALREADY_EXISTS("601","this subcontractor have already exists"),
    TEAM_MAIN_CONTRACTOR_EXISTS("602","this team had a main contractor"),
    TEAM_CI_CONTRACTOR_EXISTS("604","this team had a buchong contractor"),
    TEAM_NOT_HAVE_MAIN_CONTRACTOR_EXISTS("603","this team had not a main contractor"),
    SUBCONTRACTOR_HAVE_RESUME("605","this subcontractor have resumes"),
    INSPECTION_HAVE_SAME_PERIOD("606","this inspection have same valuation period");

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
