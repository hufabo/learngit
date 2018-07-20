package com.xhw.logcollection.monitor.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "BUS_STOCK_DEAL_STATUS")
public class BusStockDealStatus {
    /**
     * 系统类别代码
     */
    @Id
    @Column(name = "JGXTLB")
    private String jgxtlb;

    /**
     * 自增主键
     */
    @Column(name = "id")
    private String id;

    /**
     * 表名
     */
    @Id
    @Column(name = "BM")
    private String bm;

    /**
     * 采集状态，0-未启动， 1-正在执行，2-已完成
     */
    @Column(name = "CJZT")
    private String cjzt;

    /**
     * 初次启动时间，精确到秒
     */
    @Column(name = "CCQDSJ")
    private Date ccqdsj;

    /**
     * 最近启动时间，精确到秒
     */
    @Column(name = "ZJQDSJ")
    private Date zjqdsj;

    /**
     * 采集完成时间，精确到秒
     */
    @Column(name = "CJWCSJ")
    private Date cjwcsj;

    /**
     * 数据总量
     */
    @Column(name = "SJZL")
    private BigDecimal sjzl;

    /**
     * 已采集数据总量
     */
    @Column(name = "CJSJZL")
    private BigDecimal cjsjzl;

    /**
     * 已采集文件数
     */
    @Column(name = "CJWJS")
    private BigDecimal cjwjs;

    /**
     * 最后一个文件名
     */
    @Column(name = "ZHWJM")
    private String zhwjm;

    /**
     * 入库状态，0-未完成，1-已完成
     */
    @Column(name = "RKZT")
    private String rkzt;

    /**
     * 入库完成时间，精确到秒
     */
    @Column(name = "RKWCSJ")
    private Date rkwcsj;

    /**
     * 已入库数据总量
     */
    @Column(name = "RKSJZL")
    private BigDecimal rksjzl;

    /**
     * 已入库文件数
     */
    @Column(name = "RKWJS")
    private BigDecimal rkwjs;

    /**
     * 错误信息描述
     */
    @Column(name = "CWXXMS")
    private String cwxxms;

    /**
     * 更新时间，精确到秒
     */
    @Column(name = "GXSJ")
    private Date gxsj;

    /**
     * 获取系统类别代码
     *
     * @return JGXTLB - 系统类别代码
     */
    public String getJgxtlb() {
        return jgxtlb;
    }

    /**
     * 设置系统类别代码
     *
     * @param jgxtlb 系统类别代码
     */
    public void setJgxtlb(String jgxtlb) {
        this.jgxtlb = jgxtlb == null ? null : jgxtlb.trim();
    }

    /**
     * 获取表名
     *
     * @return BM - 表名
     */
    public String getBm() {
        return bm;
    }

    /**
     * 设置表名
     *
     * @param bm 表名
     */
    public void setBm(String bm) {
        this.bm = bm == null ? null : bm.trim();
    }

    /**
     * 获取采集状态，0-未启动， 1-正在执行，2-已完成
     *
     * @return CJZT - 采集状态，0-未启动， 1-正在执行，2-已完成
     */
    public String getCjzt() {
        return cjzt;
    }

    /**
     * 设置采集状态，0-未启动， 1-正在执行，2-已完成
     *
     * @param cjzt 采集状态，0-未启动， 1-正在执行，2-已完成
     */
    public void setCjzt(String cjzt) {
        this.cjzt = cjzt == null ? null : cjzt.trim();
    }

    /**
     * 获取初次启动时间，精确到秒
     *
     * @return CCQDSJ - 初次启动时间，精确到秒
     */
    public Date getCcqdsj() {
        return ccqdsj;
    }

    /**
     * 设置初次启动时间，精确到秒
     *
     * @param ccqdsj 初次启动时间，精确到秒
     */
    public void setCcqdsj(Date ccqdsj) {
        this.ccqdsj = ccqdsj;
    }

    /**
     * 获取最近启动时间，精确到秒
     *
     * @return ZJQDSJ - 最近启动时间，精确到秒
     */
    public Date getZjqdsj() {
        return zjqdsj;
    }

    /**
     * 设置最近启动时间，精确到秒
     *
     * @param zjqdsj 最近启动时间，精确到秒
     */
    public void setZjqdsj(Date zjqdsj) {
        this.zjqdsj = zjqdsj;
    }

    /**
     * 获取采集完成时间，精确到秒
     *
     * @return CJWCSJ - 采集完成时间，精确到秒
     */
    public Date getCjwcsj() {
        return cjwcsj;
    }

    /**
     * 设置采集完成时间，精确到秒
     *
     * @param cjwcsj 采集完成时间，精确到秒
     */
    public void setCjwcsj(Date cjwcsj) {
        this.cjwcsj = cjwcsj;
    }

    /**
     * 获取数据总量
     *
     * @return SJZL - 数据总量
     */
    public BigDecimal getSjzl() {
        return sjzl;
    }

    /**
     * 设置数据总量
     *
     * @param sjzl 数据总量
     */
    public void setSjzl(BigDecimal sjzl) {
        this.sjzl = sjzl;
    }

    /**
     * 获取已采集数据总量
     *
     * @return CJSJZL - 已采集数据总量
     */
    public BigDecimal getCjsjzl() {
        return cjsjzl;
    }

    /**
     * 设置已采集数据总量
     *
     * @param cjsjzl 已采集数据总量
     */
    public void setCjsjzl(BigDecimal cjsjzl) {
        this.cjsjzl = cjsjzl;
    }

    /**
     * 获取已采集文件数
     *
     * @return CJWJS - 已采集文件数
     */
    public BigDecimal getCjwjs() {
        return cjwjs;
    }

    /**
     * 设置已采集文件数
     *
     * @param cjwjs 已采集文件数
     */
    public void setCjwjs(BigDecimal cjwjs) {
        this.cjwjs = cjwjs;
    }

    /**
     * 获取最后一个文件名
     *
     * @return ZHWJM - 最后一个文件名
     */
    public String getZhwjm() {
        return zhwjm;
    }

    /**
     * 设置最后一个文件名
     *
     * @param zhwjm 最后一个文件名
     */
    public void setZhwjm(String zhwjm) {
        this.zhwjm = zhwjm == null ? null : zhwjm.trim();
    }

    /**
     * 获取入库状态，0-未完成，1-已完成
     *
     * @return RKZT - 入库状态，0-未完成，1-已完成
     */
    public String getRkzt() {
        return rkzt;
    }

    /**
     * 设置入库状态，0-未完成，1-已完成
     *
     * @param rkzt 入库状态，0-未完成，1-已完成
     */
    public void setRkzt(String rkzt) {
        this.rkzt = rkzt == null ? null : rkzt.trim();
    }

    /**
     * 获取入库完成时间，精确到秒
     *
     * @return RKWCSJ - 入库完成时间，精确到秒
     */
    public Date getRkwcsj() {
        return rkwcsj;
    }

    /**
     * 设置入库完成时间，精确到秒
     *
     * @param rkwcsj 入库完成时间，精确到秒
     */
    public void setRkwcsj(Date rkwcsj) {
        this.rkwcsj = rkwcsj;
    }

    /**
     * 获取已入库数据总量
     *
     * @return RKSJZL - 已入库数据总量
     */
    public BigDecimal getRksjzl() {
        return rksjzl;
    }

    /**
     * 设置已入库数据总量
     *
     * @param rksjzl 已入库数据总量
     */
    public void setRksjzl(BigDecimal rksjzl) {
        this.rksjzl = rksjzl;
    }

    /**
     * 获取已入库文件数
     *
     * @return RKWJS - 已入库文件数
     */
    public BigDecimal getRkwjs() {
        return rkwjs;
    }

    /**
     * 设置已入库文件数
     *
     * @param rkwjs 已入库文件数
     */
    public void setRkwjs(BigDecimal rkwjs) {
        this.rkwjs = rkwjs;
    }

    /**
     * 获取错误信息描述
     *
     * @return CWXXMS - 错误信息描述
     */
    public String getCwxxms() {
        return cwxxms;
    }

    /**
     * 设置错误信息描述
     *
     * @param cwxxms 错误信息描述
     */
    public void setCwxxms(String cwxxms) {
        this.cwxxms = cwxxms == null ? null : cwxxms.trim();
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BusStockDealStatus{" +
                "jgxtlb='" + jgxtlb + '\'' +
                ", id='" + id + '\'' +
                ", bm='" + bm + '\'' +
                ", cjzt='" + cjzt + '\'' +
                ", ccqdsj=" + ccqdsj +
                ", zjqdsj=" + zjqdsj +
                ", cjwcsj=" + cjwcsj +
                ", sjzl=" + sjzl +
                ", cjsjzl=" + cjsjzl +
                ", cjwjs=" + cjwjs +
                ", zhwjm='" + zhwjm + '\'' +
                ", rkzt='" + rkzt + '\'' +
                ", rkwcsj=" + rkwcsj +
                ", rksjzl=" + rksjzl +
                ", rkwjs=" + rkwjs +
                ", cwxxms='" + cwxxms + '\'' +
                ", gxsj=" + gxsj +
                '}';
    }
}