package com.xhw.logcollection.job.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "BUS_CFG_GLOBAL")
public class BusCfgGlobal {
    /**
     * 关键字（参数标识）
     */
    @Id
    @Column(name = "GJZ")
    private String gjz;

    /**
     * 参数名称
     */
    @Column(name = "CSMC")
    private String csmc;

    /**
     * 默认值
     */
    @Column(name = "MRZ")
    private String mrz;

    /**
     * 启用标记，0-否，1-是
     */
    @Column(name = "QYBJ")
    private String qybj;

    /**
     * 更新时间，精确到秒
     */
    @Column(name = "GXSJ")
    private Date gxsj;

    /**
     * 获取关键字（参数标识）
     *
     * @return GJZ - 关键字（参数标识）
     */
    public String getGjz() {
        return gjz;
    }

    /**
     * 设置关键字（参数标识）
     *
     * @param gjz 关键字（参数标识）
     */
    public void setGjz(String gjz) {
        this.gjz = gjz == null ? null : gjz.trim();
    }

    /**
     * 获取参数名称
     *
     * @return CSMC - 参数名称
     */
    public String getCsmc() {
        return csmc;
    }

    /**
     * 设置参数名称
     *
     * @param csmc 参数名称
     */
    public void setCsmc(String csmc) {
        this.csmc = csmc == null ? null : csmc.trim();
    }

    /**
     * 获取默认值
     *
     * @return MRZ - 默认值
     */
    public String getMrz() {
        return mrz;
    }

    /**
     * 设置默认值
     *
     * @param mrz 默认值
     */
    public void setMrz(String mrz) {
        this.mrz = mrz == null ? null : mrz.trim();
    }

    /**
     * 获取启用标记，0-否，1-是
     *
     * @return QYBJ - 启用标记，0-否，1-是
     */
    public String getQybj() {
        return qybj;
    }

    /**
     * 设置启用标记，0-否，1-是
     *
     * @param qybj 启用标记，0-否，1-是
     */
    public void setQybj(String qybj) {
        this.qybj = qybj == null ? null : qybj.trim();
    }

    /**
     * 获取更新时间，精确到秒
     *
     * @return GXSJ - 更新时间，精确到秒
     */
    public Date getGxsj() {
        return gxsj;
    }

    /**
     * 设置更新时间，精确到秒
     *
     * @param gxsj 更新时间，精确到秒
     */
    public void setGxsj(Date gxsj) {
        this.gxsj = gxsj;
    }

    @Override
    public String toString() {
        return "BusCfgGlobal{" +
                "gjz='" + gjz + '\'' +  //关键字（参数标识）
                ", csmc='" + csmc + '\'' +  //参数名称
                ", mrz='" + mrz + '\'' +    //默认值
                ", qybj='" + qybj + '\'' +
                ", gxsj=" + gxsj +
                '}';
    }

    /**
     * 返回实体类可用于对比的内容
     * @return
     */
    public String getCompareContent(){
        return "{" +
                "gjz='" + gjz + '\'' +  //关键字（参数标识）
                ", csmc='" + csmc + '\'' +  //参数名称
                ", mrz='" + mrz + '\'' +    //默认值
                '}';
    }
}