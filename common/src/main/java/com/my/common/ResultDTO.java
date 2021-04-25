package com.my.common;

import com.my.common.exception.ErrorCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "返回结果")
public class ResultDTO <T> implements Serializable {

    @ApiModelProperty(value = "接口请求是否成功")
    private boolean success;

    @ApiModelProperty(value = "接口响应数据")
    private T data;

    @ApiModelProperty(value = "接口请求code")
    private Integer errorCode;

    @ApiModelProperty(value = "接口请求消息")
    private String message;

    public ResultDTO() {}

    public ResultDTO(boolean success, T data, Integer errorCode, String message) {
        this.success = success;
        this.data = data;
        this.errorCode = errorCode;
        this.message = message;
    }

    public ResultDTO(boolean success, String msg) {
        this.success = success;
        this.message = msg;
    }

    public static <T> ResultDTO success(){
        return new ResultDTO(Boolean.TRUE,null,null,null);
    }

    public static <T> ResultDTO success(T data){
        return new ResultDTO(Boolean.TRUE,data,null,null);
    }
    public static <T> ResultDTO fail(T data){
        return new ResultDTO(Boolean.FALSE,data,null,null);
    }

    public static <T> ResultDTO fail(String msg){
        return new ResultDTO(Boolean.FALSE,null,null,msg);
    }

    public static <T> ResultDTO fail(int errorCode, String msg){
        return new ResultDTO(Boolean.FALSE,null,errorCode,msg);
    }

    public static <T> ResultDTO fail(ErrorCode errorCode){
        return new ResultDTO(Boolean.FALSE,null,errorCode.getErrorCode(),errorCode.getErrorMsg());
    }
}
