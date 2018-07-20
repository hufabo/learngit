package com.xhw.logcollection.system.entity;

import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "SYS_CFG_REGISTER")
public class Register {
    /**
     * 记录ID
     */
    @Id
    @Column(name = "JLID")
    private String jlid;

    /**
     * 备案编号
     */
    @Column(name = "BABH")
    private String babh;

    /**
     * 接口访问授权码
     */
    @Column(name = "JKFWSQM")
    private String jkfwsqm;

    /**
     * 安管系统访问IP
     */
    @Column(name = "AGXTIP")
    private String agxtip;

    /**
     * 安管系统访问端口
     */
    @Column(name = "AGXTDK")
    private BigDecimal agxtdk;

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
     * 获取记录ID
     *
     * @return JLID - 记录ID
     */
    public String getJlid() {
        return jlid;
    }

    /**
     * 设置记录ID
     *
     * @param jlid 记录ID
     */
    public void setJlid(String jlid) {
        this.jlid = jlid == null ? null : jlid.trim();
    }

    /**
     * 获取备案编号
     *
     * @return BABH - 备案编号
     */
    public String getBabh() {
        return babh;
    }

    /**
     * 设置备案编号
     *
     * @param babh 备案编号
     */
    public void setBabh(String babh) {
        this.babh = babh == null ? null : babh.trim();
    }

    /**
     * 获取接口访问授权码
     *
     * @return JKFWSQM - 接口访问授权码
     */
    public String getJkfwsqm() {
        return jkfwsqm;
    }

    /**
     * 设置接口访问授权码
     *
     * @param jkfwsqm 接口访问授权码
     */
    public void setJkfwsqm(String jkfwsqm) {
        this.jkfwsqm = jkfwsqm == null ? null : jkfwsqm.trim();
    }

    /**
     * 获取安管系统访问IP
     *
     * @return AGXTIP - 安管系统访问IP
     */
    public String getAgxtip() {
        return agxtip;
    }

    /**
     * 设置安管系统访问IP
     *
     * @param agxtip 安管系统访问IP
     */
    public void setAgxtip(String agxtip) {
        this.agxtip = agxtip == null ? null : agxtip.trim();
    }

    /**
     * 获取安管系统访问端口
     *
     * @return AGXTDK - 安管系统访问端口
     */
    public BigDecimal getAgxtdk() {
        return agxtdk;
    }

    /**
     * 设置安管系统访问端口
     *
     * @param agxtdk 安管系统访问端口
     */
    public void setAgxtdk(BigDecimal agxtdk) {
        this.agxtdk = agxtdk;
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
}