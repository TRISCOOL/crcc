package com.crcc.common.exception;

public class CrccException extends RuntimeException{

    private ResponseCode errorCode;

    public CrccException() {
    }

    public CrccException(String message) {
        super(message);
        ResponseCode errorCode = ResponseCode.SERVER_ERROR;
        this.errorCode = errorCode;
    }

    public CrccException(String message, Throwable cause) {
        super(message, cause);
    }

    public CrccException(Throwable cause) {
        super(cause);
    }

    public CrccException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CrccException(ResponseCode code)
    {
        super(code.getMessage());
        this.errorCode = code;
    }

    public ResponseCode getErrorCode() {
        return errorCode;
    }
}
