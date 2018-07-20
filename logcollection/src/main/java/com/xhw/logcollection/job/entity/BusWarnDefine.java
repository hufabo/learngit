package com.xhw.logcollection.job.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 异常定义
 * @author 孔纲
 * @date 2018/3/2
 */
@Table(name = "bus_warn_define")
public class BusWarnDefine implements Serializable {

    /**
     * 异常编码
     */
    @Id
    @Column(name = "code")
    private String code;

    /**
     * 异常类型
     */
    @Column(name = "type")
    private String type;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 提示信息
     */
    @Column(name = "message")
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
