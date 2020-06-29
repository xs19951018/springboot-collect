package com.my.springbootshiro.vo;

import lombok.Data;

/**
 * http 请求返回的最外层对象
 * Created by user on 2019/3/12.
 */
@Data
public class ResultVO<T> {

    /** 错误码. */
    private Integer code;

    /** 提示信息. */
    private String msg;

    /** 具体内容. */
    private T data;
}
