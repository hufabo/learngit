package com.xhw.logcollection.system.entity;

import javax.persistence.*;

@Table(name = "SYS_CFG_SHARE_PROTOCOL")
public class ShareProtocol {
    /**
     * 目录共享ID
     */
    @Id
    @Column(name = "MLGXID")
    private String mlgxid;

    /**
     * 目录共享名称
     */
    @Column(name = "MLGXMC")
    private String mlgxmc;

    /**
     * 目录共享IP
     */
    @Column(name = "MLGXIP")
    private String mlgxip;

    /**
     * 目录共享访问用户名
     */
    @Column(name = "MLGXYHM")
    private String mlgxyhm;

    /**
     * 目录共享访问密码
     */
    @Column(name = "MLGXMM")
    private String mlgxmm;

    /**
     * 归档日志文件目录
     */
    @Column(name = "MLGXGDRZ")
    private String mlgxgdrz;

    /**
     * 归档日志文件目录共享名称
     */
    @Column(name = "MLGXGDRZMC")
    private String mlgxgdrzmc;

    /**
     * 在线日志文件目录
     */
    @Column(name = "MLGXZXRZ")
    private String mlgxzxrz;

    /**
     * 在线日志文件目录共享名称
     */
    @Column(name = "MLGXZXRZMC")
    private String mlgxzxrzmc;

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
     * 系统类型（1=window/2=Linux）
     */
    @Column(name = "XTLB")
    private String xtlb;

    /**
     * 协议类型(1=SMB/2=NFS)
     */
    @Column(name = "XYLX")
    private String xylx;

    /**
     * 获取目录共享ID
     *
     * @return MLGXID - 目录共享ID
     */
    public String getMlgxid() {
        return mlgxid;
    }

    /**
     * 设置目录共享ID
     *
     * @param mlgxid 目录共享ID
     */
    public void setMlgxid(String mlgxid) {
        this.mlgxid = mlgxid == null ? null : mlgxid.trim();
    }

    /**
     * 获取目录共享名称
     *
     * @return MLGXMC - 目录共享名称
     */
    public String getMlgxmc() {
        return mlgxmc;
    }

    /**
     * 设置目录共享名称
     *
     * @param mlgxmc 目录共享名称
     */
    public void setMlgxmc(String mlgxmc) {
        this.mlgxmc = mlgxmc == null ? null : mlgxmc.trim();
    }

    /**
     * 获取目录共享IP
     *
     * @return MLGXIP - 目录共享IP
     */
    public String getMlgxip() {
        return mlgxip;
    }

    /**
     * 设置目录共享IP
     *
     * @param mlgxip 目录共享IP
     */
    public void setMlgxip(String mlgxip) {
        this.mlgxip = mlgxip == null ? null : mlgxip.trim();
    }

    /**
     * 获取目录共享访问用户名
     *
     * @return MLGXYHM - 目录共享访问用户名
     */
    public String getMlgxyhm() {
        return mlgxyhm;
    }

    /**
     * 设置目录共享访问用户名
     *
     * @param mlgxyhm 目录共享访问用户名
     */
    public void setMlgxyhm(String mlgxyhm) {
        this.mlgxyhm = mlgxyhm == null ? null : mlgxyhm.trim();
    }

    /**
     * 获取目录共享访问密码
     *
     * @return MLGXMM - 目录共享访问密码
     */
    public String getMlgxmm() {
        return mlgxmm;
    }

    /**
     * 设置目录共享访问密码
     *
     * @param mlgxmm 目录共享访问密码
     */
    public void setMlgxmm(String mlgxmm) {
        this.mlgxmm = mlgxmm == null ? null : mlgxmm.trim();
    }

    /**
     * 获取归档日志文件目录
     *
     * @return MLGXGDRZ - 归档日志文件目录
     */
    public String getMlgxgdrz() {
        return mlgxgdrz;
    }

    /**
     * 设置归档日志文件目录
     *
     * @param mlgxgdrz 归档日志文件目录
     */
    public void setMlgxgdrz(String mlgxgdrz) {
        this.mlgxgdrz = mlgxgdrz == null ? null : mlgxgdrz.trim();
    }

    /**
     * 获取归档日志文件目录共享名称
     *
     * @return MLGXGDRZMC - 归档日志文件目录共享名称
     */
    public String getMlgxgdrzmc() {
        return mlgxgdrzmc;
    }

    /**
     * 设置归档日志文件目录共享名称
     *
     * @param mlgxgdrzmc 归档日志文件目录共享名称
     */
    public void setMlgxgdrzmc(String mlgxgdrzmc) {
        this.mlgxgdrzmc = mlgxgdrzmc == null ? null : mlgxgdrzmc.trim();
    }

    /**
     * 获取在线日志文件目录
     *
     * @return MLGXZXRZ - 在线日志文件目录
     */
    public String getMlgxzxrz() {
        return mlgxzxrz;
    }

    /**
     * 设置在线日志文件目录
     *
     * @param mlgxzxrz 在线日志文件目录
     */
    public void setMlgxzxrz(String mlgxzxrz) {
        this.mlgxzxrz = mlgxzxrz == null ? null : mlgxzxrz.trim();
    }

    /**
     * 获取在线日志文件目录共享名称
     *
     * @return MLGXZXRZMC - 在线日志文件目录共享名称
     */
    public String getMlgxzxrzmc() {
        return mlgxzxrzmc;
    }

    /**
     * 设置在线日志文件目录共享名称
     *
     * @param mlgxzxrzmc 在线日志文件目录共享名称
     */
    public void setMlgxzxrzmc(String mlgxzxrzmc) {
        this.mlgxzxrzmc = mlgxzxrzmc == null ? null : mlgxzxrzmc.trim();
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

    /**
     * 获取系统类型（1=window/2=Linux）
     *
     * @return XTLB - 系统类型（1=window/2=Linux）
     */
    public String getXtlb() {
        return xtlb;
    }

    /**
     * 设置系统类型（1=window/2=Linux）
     *
     * @param xtlb 系统类型（1=window/2=Linux）
     */
    public void setXtlb(String xtlb) {
        this.xtlb = xtlb == null ? null : xtlb.trim();
    }

    /**
     * 获取协议类型(1=SMB/2=NFS)
     *
     * @return XYLX - 协议类型(1=SMB/2=NFS)
     */
    public String getXylx() {
        return xylx;
    }

    /**
     * 设置协议类型(1=SMB/2=NFS)
     *
     * @param xylx 协议类型(1=SMB/2=NFS)
     */
    public void setXylx(String xylx) {
        this.xylx = xylx == null ? null : xylx.trim();
    }
}