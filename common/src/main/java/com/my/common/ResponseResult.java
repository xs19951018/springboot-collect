package com.my.common;

import com.my.common.exception.ErrorCode;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseResult<T> implements Serializable {
    /**
     * 接口请求id
     */
    private Long requestId = System.currentTimeMillis();

    /**
     * 状态(true:成功,false:失败)
     */
    private boolean status = false;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 异常信息（不提示给用户） 便于抓包排查问题，特别是未知系统异常，一般在debug模式下启用
     */
    private String exception;

    /**
     * 响应码
     */
    private int responseCode;

    /**
     *  是否还有下一页
     */
    private boolean isHasNext;

    /**
     * 响应时间
     */
    private long timestamp = System.currentTimeMillis();

    /**
     * 业务数据
     */
    private T entry;

    public ResponseResult(int responseCode,String message) {
        this.responseCode = responseCode;
        this.message = message;
    }

    public ResponseResult(boolean success, T data, int errorCode, String message) {
        this.status = success;
        this.entry = data;
        this.responseCode = errorCode;
        this.message = message;
    }

    public ResponseResult(boolean success, String msg) {
        this.status = success;
        this.message = msg;
    }

    public ResponseResult(boolean success) {
        this.status = success;
    }

    public static <T> ResponseResult success(){
        return new ResponseResult(Boolean.TRUE,null,0,null);
    }

    public static <T> ResponseResult success(T data){
        return new ResponseResult(Boolean.TRUE,data,0,null);
    }

    public static <T> ResponseResult fail(String msg){
        return new ResponseResult(Boolean.FALSE,null,-1,msg);
    }

    public static <T> ResponseResult fail(int errorCode, String msg){
        return new ResponseResult(Boolean.FALSE,null,errorCode,msg);
    }

    public static <T> ResponseResult fail(ErrorCode errorCode){
        return new ResponseResult(Boolean.FALSE,null,errorCode.getErrorCode(),errorCode.getErrorMsg());
    }

}
