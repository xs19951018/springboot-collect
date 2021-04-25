package com.my.common.exception;

/**
 *   
 *  @Description:服务层异常类
 *  @author litu  
 *  @date 2018/5/24 22:42  
 */
public class ServiceException extends RuntimeException{

    private static final long serialVersionUID = 1306929421064469489L;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    private int errorCode = -1;

    public ServiceException(int errorCode , String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ServiceException(ErrorCode errorCodeEnum) {
        super(errorCodeEnum.getErrorMsg());
        this.errorCode = errorCodeEnum.getErrorCode();
    }

    public ServiceException(ErrorCode errorCodeEnum, String message) {
        super(message);
        this.errorCode = errorCodeEnum.getErrorCode();
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
