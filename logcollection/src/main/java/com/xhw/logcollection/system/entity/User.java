package com.xhw.logcollection.system.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "SYS_USER")
public class User {
    /**
     * 用户ID
     */
    @Id
    @Column(name = "YHID")
    private String yhid;

    /**
     * 用户名
     */
    @Column(name = "YHMC")
    private String yhmc;

    /**
     * 密码
     */
    @Column(name = "DLMM")
    private String dlmm;

    /**
     * 电话号码
     */
    @Column(name = "DHHM")
    private String dhhm;

    /**
     * 邮箱地址
     */
    @Column(name = "YXDZ")
    private String yxdz;

    /**
     * 备注
     */
    @Column(name = "BZ")
    private String bz;

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
     * 获取用户ID
     *
     * @return YHID - 用户ID
     */
    public String getYhid() {
        return yhid;
    }

    /**
     * 设置用户ID
     *
     * @param yhid 用户ID
     */
    public void setYhid(String yhid) {
        this.yhid = yhid == null ? null : yhid.trim();
    }

    /**
     * 获取用户名
     *
     * @return YHMC - 用户名
     */
    public String getYhmc() {
        return yhmc;
    }

    /**
     * 设置用户名
     *
     * @param yhmc 用户名
     */
    public void setYhmc(String yhmc) {
        this.yhmc = yhmc == null ? null : yhmc.trim();
    }

    /**
     * 获取密码
     *
     * @return DLMM - 密码
     */
    public String getDlmm() {
        return dlmm;
    }

    /**
     * 设置密码
     *
     * @param dlmm 密码
     */
    public void setDlmm(String dlmm) {
        this.dlmm = dlmm == null ? null : dlmm.trim();
    }

    /**
     * 获取电话号码
     *
     * @return DHHM - 电话号码
     */
    public String getDhhm() {
        return dhhm;
    }

    /**
     * 设置电话号码
     *
     * @param dhhm 电话号码
     */
    public void setDhhm(String dhhm) {
        this.dhhm = dhhm == null ? null : dhhm.trim();
    }

    /**
     * 获取邮箱地址
     *
     * @return YXDZ - 邮箱地址
     */
    public String getYxdz() {
        return yxdz;
    }

    /**
     * 设置邮箱地址
     *
     * @param yxdz 邮箱地址
     */
    public void setYxdz(String yxdz) {
        this.yxdz = yxdz == null ? null : yxdz.trim();
    }

    /**
     * 获取备注
     *
     * @return BZ - 备注
     */
    public String getBz() {
        return bz;
    }

    /**
     * 设置备注
     *
     * @param bz 备注
     */
    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
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