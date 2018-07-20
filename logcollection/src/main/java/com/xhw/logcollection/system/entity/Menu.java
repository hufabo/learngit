package com.xhw.logcollection.system.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "SYS_MENU")
public class Menu {
    /**
     * 菜单编号
     */
    @Id
    @Column(name = "CDID")
    private String cdid;

    /**
     * 菜单名称
     */
    @Column(name = "CDMC")
    private String cdmc;

    /**
     * 菜单图标
     */
    @Column(name = "CDTB")
    private String cdtb;

    /**
     * 菜单链接地址
     */
    @Column(name = "CDLJDZ")
    private String cdljdz;

    /**
     * 父级菜单编号
     */
    @Column(name = "FJCDID")
    private String fjcdid;

    /**
     * 菜单序号
     */
    @Column(name = "CDXH")
    private BigDecimal cdxh;

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
     * 获取菜单编号
     *
     * @return CDID - 菜单编号
     */
    public String getCdid() {
        return cdid;
    }

    /**
     * 设置菜单编号
     *
     * @param cdid 菜单编号
     */
    public void setCdid(String cdid) {
        this.cdid = cdid == null ? null : cdid.trim();
    }

    /**
     * 获取菜单名称
     *
     * @return CDMC - 菜单名称
     */
    public String getCdmc() {
        return cdmc;
    }

    /**
     * 设置菜单名称
     *
     * @param cdmc 菜单名称
     */
    public void setCdmc(String cdmc) {
        this.cdmc = cdmc == null ? null : cdmc.trim();
    }

    /**
     * 获取菜单图标
     *
     * @return CDTB - 菜单图标
     */
    public String getCdtb() {
        return cdtb;
    }

    /**
     * 设置菜单图标
     *
     * @param cdtb 菜单图标
     */
    public void setCdtb(String cdtb) {
        this.cdtb = cdtb == null ? null : cdtb.trim();
    }

    /**
     * 获取菜单链接地址
     *
     * @return CDLJDZ - 菜单链接地址
     */
    public String getCdljdz() {
        return cdljdz;
    }

    /**
     * 设置菜单链接地址
     *
     * @param cdljdz 菜单链接地址
     */
    public void setCdljdz(String cdljdz) {
        this.cdljdz = cdljdz == null ? null : cdljdz.trim();
    }

    /**
     * 获取父级菜单编号
     *
     * @return FJCDID - 父级菜单编号
     */
    public String getFjcdid() {
        return fjcdid;
    }

    /**
     * 设置父级菜单编号
     *
     * @param fjcdid 父级菜单编号
     */
    public void setFjcdid(String fjcdid) {
        this.fjcdid = fjcdid == null ? null : fjcdid.trim();
    }

    /**
     * 获取菜单序号
     *
     * @return CDXH - 菜单序号
     */
    public BigDecimal getCdxh() {
        return cdxh;
    }

    /**
     * 设置菜单序号
     *
     * @param cdxh 菜单序号
     */
    public void setCdxh(BigDecimal cdxh) {
        this.cdxh = cdxh;
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