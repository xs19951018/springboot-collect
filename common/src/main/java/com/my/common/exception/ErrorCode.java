package com.my.common.exception;

/**
 * 业务异常接口：
 * 10000 用户信息类
 * 20000 商品类
 * 30000 订单类
 */
public interface ErrorCode {
    int getErrorCode();

    String getErrorMsg();
}
