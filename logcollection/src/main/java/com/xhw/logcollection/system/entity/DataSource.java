package com.xhw.logcollection.system.entity;

import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "SYS_CFG_DATASOURCE")
public class DataSource {
    /**
     * 数据源ID
     */
    @Id
    @Column(name = "SJYID")
    private String sjyid;

    /**
     * 数据源名称
     */
    @Column(name = "SJYMC")
    private String sjymc;

    /**
     * IP 地址
     */
    @Column(name = "IP")
    private String ip;

    /**
     * 端口
     */
    @Column(name = "PORT")
    private BigDecimal port;

    /**
     * SID
     */
    @Column(name = "SID")
    private String sid;

    /**
     * Service Name
     */
    @Column(name = "SN")
    private String sn;

    /**
     * schema
     */
    @Column(name = "ORA_SCHEMA")
    private String oraSchema;

    /**
     * 数据库用户名
     */
    @Column(name = "ORA_USER")
    private String oraUser;

    /**
     * 用户密码
     */
    @Column(name = "ORA_PASSWORD")
    private String oraPassword;

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
     * 是否RAC模式 1-是 0-不是
     */
    @Column(name = "RAC")
    private String rac;

    /**
     * 获取数据源ID
     *
     * @return SJYID - 数据源ID
     */
    public String getSjyid() {
        return sjyid;
    }

    /**
     * 设置数据源ID
     *
     * @param sjyid 数据源ID
     */
    public void setSjyid(String sjyid) {
        this.sjyid = sjyid == null ? null : sjyid.trim();
    }

    /**
     * 获取数据源名称
     *
     * @return SJYMC - 数据源名称
     */
    public String getSjymc() {
        return sjymc;
    }

    /**
     * 设置数据源名称
     *
     * @param sjymc 数据源名称
     */
    public void setSjymc(String sjymc) {
        this.sjymc = sjymc == null ? null : sjymc.trim();
    }

    /**
     * 获取IP 地址
     *
     * @return IP - IP 地址
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置IP 地址
     *
     * @param ip IP 地址
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * 获取端口
     *
     * @return PORT - 端口
     */
    public BigDecimal getPort() {
        return port;
    }

    /**
     * 设置端口
     *
     * @param port 端口
     */
    public void setPort(BigDecimal port) {
        this.port = port;
    }

    /**
     * 获取SID
     *
     * @return SID - SID
     */
    public String getSid() {
        return sid;
    }

    /**
     * 设置SID
     *
     * @param sid SID
     */
    public void setSid(String sid) {
        this.sid = sid == null ? null : sid.trim();
    }

    /**
     * 获取Service Name
     *
     * @return SN - Service Name
     */
    public String getSn() {
        return sn;
    }

    /**
     * 设置Service Name
     *
     * @param sn Service Name
     */
    public void setSn(String sn) {
        this.sn = sn == null ? null : sn.trim();
    }

    /**
     * 获取schema
     *
     * @return ORA_SCHEMA - schema
     */
    public String getOraSchema() {
        return oraSchema;
    }

    /**
     * 设置schema
     *
     * @param oraSchema schema
     */
    public void setOraSchema(String oraSchema) {
        this.oraSchema = oraSchema == null ? null : oraSchema.trim();
    }

    /**
     * 获取数据库用户名
     *
     * @return ORA_USER - 数据库用户名
     */
    public String getOraUser() {
        return oraUser;
    }

    /**
     * 设置数据库用户名
     *
     * @param oraUser 数据库用户名
     */
    public void setOraUser(String oraUser) {
        this.oraUser = oraUser == null ? null : oraUser.trim();
    }

    /**
     * 获取用户密码
     *
     * @return ORA_PASSWORD - 用户密码
     */
    public String getOraPassword() {
        return oraPassword;
    }

    /**
     * 设置用户密码
     *
     * @param oraPassword 用户密码
     */
    public void setOraPassword(String oraPassword) {
        this.oraPassword = oraPassword == null ? null : oraPassword.trim();
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

    public String getRac() {
        return rac;
    }

    public void setRac(String rac) {
        this.rac = rac;
    }
}