package com.my.elasticsearch.entity.constants;

import com.my.common.exception.ErrorCode;

/**
 * elasticearch 错误码
 */
public enum ElasticErrCodeEnum implements ErrorCode {

    INDEX_NO_DEFINE(1001, "索引未定义"),
    INDEX_NOT_EXIST(1002, "索引不存在");

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
