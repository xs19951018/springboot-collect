package com.my.common.aspect;

import com.my.common.ResponseResult;
import com.my.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * 自定义异常处理
 */
@Slf4j
@RestControllerAdvice
public class BaseExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    public ResponseResult handle(Exception e) {
        log.error("系统异常：{}", e);
        return ResponseResult.fail(-1, "系统异常,请联系管理员");
    }

    @ExceptionHandler(value = ServiceException.class)
    public ResponseResult handle(ServiceException e) {
        log.error("业务异常：{}", e.getMessage());
        return ResponseResult.fail(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseResult handle(MethodArgumentNotValidException e) {
        log.error("接口入参校验失败：{}", e.getMessage());
        return ResponseResult.fail(e.getMessage());
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseResult handle(ConstraintViolationException e) {
        log.error("接口入参校验失败：{}", e.getMessage());
        return ResponseResult.fail(e.getMessage());
    }
}
