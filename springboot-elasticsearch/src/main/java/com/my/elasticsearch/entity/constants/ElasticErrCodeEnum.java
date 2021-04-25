package com.my.elasticsearch.entity.constants;

import com.my.common.exception.ErrorCode;

public enum ElasticErrCodeEnum implements ErrorCode {

    //错误码
    SYSTEM_ERROR(1001, "系统处理失败"),

    PARAM_EMPTY(1002,"参数为空"),

    PARAM_ERROR(1003,"参数异常"),

    MQ_LOCAL_TRANSACTION_ERROR(2001,"MQ执行本地事务异常"),

    MQ_SEND_ERROR(2002,"MQ发送消息失败"),

    DEFAULT_SUCCESS(0, "success") ;

    private int errorCode;

    private String errorMsg;

    ElasticErrCodeEnum(int errorCode, String errorMsg) {
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
