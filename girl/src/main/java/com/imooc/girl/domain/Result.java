package com.imooc.girl.domain;

import javax.persistence.Entity;

public class Result<T> {
    //错误代码
    private Integer code;
    //错误提示信息
    private String msg;
    //数据详情
    private  T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
