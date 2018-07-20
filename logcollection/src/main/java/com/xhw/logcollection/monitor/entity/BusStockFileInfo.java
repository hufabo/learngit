package com.xhw.logcollection.monitor.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "BUS_STOCK_FILE_INFO")
public class BusStockFileInfo {
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
    @Column(name = "BM")
    private String bm;

    /**
     * 数据块编号
     */
    @Column(name = "SJKBH")
    private BigDecimal sjkbh;

    /**
     * 文件名，唯一索引
     */
    @Column(name = "WJM")
    private String wjm;

    /**
     * MD5值
     */
    @Column(name = "MD")
    private String md;

    /**
     * 文件大小，单位：KB
     */
    @Column(name = "WJDX")
    private BigDecimal wjdx;

    /**
     * 数据量
     */
    @Column(name = "SLJ")
    private BigDecimal slj;

    /**
     * 时间戳起
     */
    @Column(name = "SJCQ")
    private Date sjcq;

    /**
     * 时间戳止
     */
    @Column(name = "SJCZ")
    private Date sjcz;

    /**
     * 文件状态。1-采集，2-上传文件服务器，3-上传部局，4-入库，5要求重传，6-本地文件丢失，7-数据丢失
     */
    @Column(name = "WJZT")
    private String wjzt;

    /**
     * 生成时间，精确到秒
     */
    @Column(name = "SCSJ")
    private Date scsj;

    /**
     * 上传服务器时间，精确到秒
     */
    @Column(name = "SCFWQSJ")
    private Date scfwqsj;

    /**
     * 上传部局时间，精确到秒
     */
    @Column(name = "SCBJSJ")
    private Date scbjsj;

    /**
     * 入库时间，精确到秒
     */
    @Column(name = "RKSJ")
    private Date rksj;

    /**
     * 错误状态。0-无错误，1-出错
     */
    @Column(name = "CWZT")
    private String cwzt;

    /**
     * 错误信息描述
     */
    @Column(name = "CWXXMS")
    private String cwxxms;

    /**
     * 出错时间，精确到秒
     */
    @Column(name = "CCSJ")
    private Date ccsj;

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

    public BigDecimal getSjkbh() {
        return sjkbh;
    }

    public void setSjkbh(BigDecimal sjkbh) {
        this.sjkbh = sjkbh;
    }

    /**
     * 获取文件名，唯一索引
     *
     * @return WJM - 文件名，唯一索引
     */
    public String getWjm() {
        return wjm;
    }

    /**
     * 设置文件名，唯一索引
     *
     * @param wjm 文件名，唯一索引
     */
    public void setWjm(String wjm) {
        this.wjm = wjm == null ? null : wjm.trim();
    }

    /**
     * 获取MD5值
     *
     * @return MD - MD5值
     */
    public String getMd() {
        return md;
    }

    /**
     * 设置MD5值
     *
     * @param md MD5值
     */
    public void setMd(String md) {
        this.md = md == null ? null : md.trim();
    }

    /**
     * 获取文件大小，单位：KB
     *
     * @return WJDX - 文件大小，单位：KB
     */
    public BigDecimal getWjdx() {
        return wjdx;
    }

    /**
     * 设置文件大小，单位：KB
     *
     * @param wjdx 文件大小，单位：KB
     */
    public void setWjdx(BigDecimal wjdx) {
        this.wjdx = wjdx;
    }

    /**
     * 获取数据量
     *
     * @return SLJ - 数据量
     */
    public BigDecimal getSlj() {
        return slj;
    }

    /**
     * 设置数据量
     *
     * @param slj 数据量
     */
    public void setSlj(BigDecimal slj) {
        this.slj = slj;
    }

    /**
     * 获取时间戳起
     *
     * @return SJCQ - 时间戳起
     */
    public Date getSjcq() {
        return sjcq;
    }

    /**
     * 设置时间戳起
     *
     * @param sjcq 时间戳起
     */
    public void setSjcq(Date sjcq) {
        this.sjcq = sjcq;
    }

    /**
     * 获取时间戳止
     *
     * @return SJCZ - 时间戳止
     */
    public Date getSjcz() {
        return sjcz;
    }

    /**
     * 设置时间戳止
     *
     * @param sjcz 时间戳止
     */
    public void setSjcz(Date sjcz) {
        this.sjcz = sjcz;
    }

    /**
     * 获取文件状态。1-采集，2-上传文件服务器，3-上传部局，4-入库，5要求重传，6-本地文件丢失，7-数据丢失
     *
     * @return WJZT - 文件状态。1-采集，2-上传文件服务器，3-上传部局，4-入库，5要求重传，6-本地文件丢失，7-数据丢失
     */
    public String getWjzt() {
        return wjzt;
    }

    /**
     * 设置文件状态。1-采集，2-上传文件服务器，3-上传部局，4-入库，5要求重传，6-本地文件丢失，7-数据丢失
     *
     * @param wjzt 文件状态。1-采集，2-上传文件服务器，3-上传部局，4-入库，5要求重传，6-本地文件丢失，7-数据丢失
     */
    public void setWjzt(String wjzt) {
        this.wjzt = wjzt == null ? null : wjzt.trim();
    }

    /**
     * 获取生成时间，精确到秒
     *
     * @return SCSJ - 生成时间，精确到秒
     */
    public Date getScsj() {
        return scsj;
    }

    /**
     * 设置生成时间，精确到秒
     *
     * @param scsj 生成时间，精确到秒
     */
    public void setScsj(Date scsj) {
        this.scsj = scsj;
    }

    /**
     * 获取上传服务器时间，精确到秒
     *
     * @return SCFWQSJ - 上传服务器时间，精确到秒
     */
    public Date getScfwqsj() {
        return scfwqsj;
    }

    /**
     * 设置上传服务器时间，精确到秒
     *
     * @param scfwqsj 上传服务器时间，精确到秒
     */
    public void setScfwqsj(Date scfwqsj) {
        this.scfwqsj = scfwqsj;
    }

    /**
     * 获取上传部局时间，精确到秒
     *
     * @return SCBJSJ - 上传部局时间，精确到秒
     */
    public Date getScbjsj() {
        return scbjsj;
    }

    /**
     * 设置上传部局时间，精确到秒
     *
     * @param scbjsj 上传部局时间，精确到秒
     */
    public void setScbjsj(Date scbjsj) {
        this.scbjsj = scbjsj;
    }

    /**
     * 获取入库时间，精确到秒
     *
     * @return RKSJ - 入库时间，精确到秒
     */
    public Date getRksj() {
        return rksj;
    }

    /**
     * 设置入库时间，精确到秒
     *
     * @param rksj 入库时间，精确到秒
     */
    public void setRksj(Date rksj) {
        this.rksj = rksj;
    }

    /**
     * 获取错误状态。0-无错误，1-出错
     *
     * @return CWZT - 错误状态。0-无错误，1-出错
     */
    public String getCwzt() {
        return cwzt;
    }

    /**
     * 设置错误状态。0-无错误，1-出错
     *
     * @param cwzt 错误状态。0-无错误，1-出错
     */
    public void setCwzt(String cwzt) {
        this.cwzt = cwzt == null ? null : cwzt.trim();
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
     * 获取出错时间，精确到秒
     *
     * @return CCSJ - 出错时间，精确到秒
     */
    public Date getCcsj() {
        return ccsj;
    }

    /**
     * 设置出错时间，精确到秒
     *
     * @param ccsj 出错时间，精确到秒
     */
    public void setCcsj(Date ccsj) {
        this.ccsj = ccsj;
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
}