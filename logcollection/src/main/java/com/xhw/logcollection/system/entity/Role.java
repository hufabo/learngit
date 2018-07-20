package com.xhw.logcollection.system.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "SYS_ROLE")
public class Role {
    /**
     * 角色ID
     */
    @Id
    @Column(name = "JSID")
    private String jsid;

    /**
     * 角色名称
     */
    @Column(name = "JSMC")
    private String jsmc;

    /**
     * 角色描述
     */
    @Column(name = "JSMS")
    private String jsms;

    /**
     * 创建日期。系统时间，格式为yyyy-MM-dd hh:mm:ss
     */
    @Column(name = "CJRQ")
    private Date cjrq;

    /**
     * 状态，1可用，2不可用，3已删除，4其他
     */
    @Column(name = "ZT")
    private String zt;

    /**
     * 获取角色ID
     *
     * @return JSID - 角色ID
     */
    public String getJsid() {
        return jsid;
    }

    /**
     * 设置角色ID
     *
     * @param jsid 角色ID
     */
    public void setJsid(String jsid) {
        this.jsid = jsid == null ? null : jsid.trim();
    }

    /**
     * 获取角色名称
     *
     * @return JSMC - 角色名称
     */
    public String getJsmc() {
        return jsmc;
    }

    /**
     * 设置角色名称
     *
     * @param jsmc 角色名称
     */
    public void setJsmc(String jsmc) {
        this.jsmc = jsmc == null ? null : jsmc.trim();
    }

    /**
     * 获取角色描述
     *
     * @return JSMS - 角色描述
     */
    public String getJsms() {
        return jsms;
    }

    /**
     * 设置角色描述
     *
     * @param jsms 角色描述
     */
    public void setJsms(String jsms) {
        this.jsms = jsms == null ? null : jsms.trim();
    }

    /**
     * 获取创建日期。系统时间，格式为yyyy-MM-dd hh:mm:ss
     *
     * @return CJRQ - 创建日期。系统时间，格式为yyyy-MM-dd hh:mm:ss
     */
    public Date getCjrq() {
        return cjrq;
    }

    /**
     * 设置创建日期。系统时间，格式为yyyy-MM-dd hh:mm:ss
     *
     * @param cjrq 创建日期。系统时间，格式为yyyy-MM-dd hh:mm:ss
     */
    public void setCjrq(Date cjrq) {
        this.cjrq = cjrq;
    }

    /**
     * 获取状态，1可用，2不可用，3已删除，4其他
     *
     * @return ZT - 状态，1可用，2不可用，3已删除，4其他
     */
    public String getZt() {
        return zt;
    }

    /**
     * 设置状态，1可用，2不可用，3已删除，4其他
     *
     * @param zt 状态，1可用，2不可用，3已删除，4其他
     */
    public void setZt(String zt) {
        this.zt = zt == null ? null : zt.trim();
    }
}