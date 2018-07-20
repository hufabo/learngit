package com.xhw.logcollection.exception;

/**
 * 自定义的参数异常类
 * 用于web接口或者ws接口参数异常时返回具体的错误信息
 * @author 孔纲
 * @date 2018/3/6
 */
public class ParameterException extends RuntimeException {
    public ParameterException(String message) {
        super(message);
    }
}
