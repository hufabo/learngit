package com.xhw.logcollection.system.entity;

import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "SYS_CFG_AGENT")
public class Agent {
    /**
     * ID
     */
    @Id
    @Column(name = "DLID")
    private String dlid;

    /**
     * 代理名称
     */
    @Column(name = "DLMC")
    private String dlmc;

    /**
     * 代理IP
     */
    @Column(name = "DLIP")
    private String dlip;

    /**
     * 代理端口号
     */
    @Column(name = "DLDKH")
    private BigDecimal dldkh;

    /**
     * 归档日志目录
     */
    @Column(name = "GDRZML")
    private String gdrzml;

    /**
     * 在线日志目录
     */
    @Column(name = "ZXRZML")
    private String zxrzml;

    /**
     * 状态。1=启用，0=未启用
     */
    @Column(name = "ZT")
    private String zt;

    /**
     * 记录状态 1=正常 2=删除
     */
    @Column(name = "JLZT")
    private String jlzt;

    /**
     * 备注
     */
    @Column(name = "BZ")
    private String bz;

    /**
     * 系统类别代码，从全局参数获取
     */
    @Column(name = "JGXTLB")
    private String jgxtlb;

    /**
     * 获取ID
     *
     * @return DLID - ID
     */
    public String getDlid() {
        return dlid;
    }

    /**
     * 设置ID
     *
     * @param dlid ID
     */
    public void setDlid(String dlid) {
        this.dlid = dlid == null ? null : dlid.trim();
    }

    /**
     * 获取代理名称
     *
     * @return DLMC - 代理名称
     */
    public String getDlmc() {
        return dlmc;
    }

    /**
     * 设置代理名称
     *
     * @param dlmc 代理名称
     */
    public void setDlmc(String dlmc) {
        this.dlmc = dlmc == null ? null : dlmc.trim();
    }

    /**
     * 获取代理IP
     *
     * @return DLIP - 代理IP
     */
    public String getDlip() {
        return dlip;
    }

    /**
     * 设置代理IP
     *
     * @param dlip 代理IP
     */
    public void setDlip(String dlip) {
        this.dlip = dlip == null ? null : dlip.trim();
    }

    /**
     * 获取代理端口号
     *
     * @return DLDKH - 代理端口号
     */
    public BigDecimal getDldkh() {
        return dldkh;
    }

    /**
     * 设置代理端口号
     *
     * @param dldkh 代理端口号
     */
    public void setDldkh(BigDecimal dldkh) {
        this.dldkh = dldkh;
    }

    /**
     * 获取归档日志目录
     *
     * @return GDRZML - 归档日志目录
     */
    public String getGdrzml() {
        return gdrzml;
    }

    /**
     * 设置归档日志目录
     *
     * @param gdrzml 归档日志目录
     */
    public void setGdrzml(String gdrzml) {
        this.gdrzml = gdrzml == null ? null : gdrzml.trim();
    }

    /**
     * 获取在线日志目录
     *
     * @return ZXRZML - 在线日志目录
     */
    public String getZxrzml() {
        return zxrzml;
    }

    /**
     * 设置在线日志目录
     *
     * @param zxrzml 在线日志目录
     */
    public void setZxrzml(String zxrzml) {
        this.zxrzml = zxrzml == null ? null : zxrzml.trim();
    }

    /**
     * 获取状态。1=启用，0=未启用
     *
     * @return ZT - 状态。1=启用，0=未启用
     */
    public String getZt() {
        return zt;
    }

    /**
     * 设置状态。1=启用，0=未启用
     *
     * @param zt 状态。1=启用，0=未启用
     */
    public void setZt(String zt) {
        this.zt = zt == null ? null : zt.trim();
    }

    /**
     * 获取记录状态 1=正常 2=删除
     *
     * @return JLZT - 记录状态 1=正常 2=删除
     */
    public String getJlzt() {
        return jlzt;
    }

    /**
     * 设置记录状态 1=正常 2=删除
     *
     * @param jlzt 记录状态 1=正常 2=删除
     */
    public void setJlzt(String jlzt) {
        this.jlzt = jlzt == null ? null : jlzt.trim();
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
     * 获取系统类别代码，从全局参数获取
     *
     * @return JGXTLB - 系统类别代码，从全局参数获取
     */
    public String getJgxtlb() {
        return jgxtlb;
    }

    /**
     * 设置系统类别代码，从全局参数获取
     *
     * @param jgxtlb 系统类别代码，从全局参数获取
     */
    public void setJgxtlb(String jgxtlb) {
        this.jgxtlb = jgxtlb == null ? null : jgxtlb.trim();
    }
}