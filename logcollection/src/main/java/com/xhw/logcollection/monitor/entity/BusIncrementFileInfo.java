package com.xhw.logcollection.monitor.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "BUS_INCREMENT_FILE_INFO")
public class BusIncrementFileInfo {
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
     * 文件名，唯一索引
     */
    @Id
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
     * Insert数据量
     */
    @Column(name = "SJLINSERT")
    private BigDecimal sjlinsert;

    /**
     * Update数据量
     */
    @Column(name = "SJLUPDATE")
    private BigDecimal sjlupdate;

    /**
     * Delete数据量
     */
    @Column(name = "SJLDELETE")
    private BigDecimal sjldelete;

    /**
     * 起始scn号
     */
    @Column(name = "SCNQ")
    private BigDecimal scnq;

    /**
     * 起始sequence号
     */
    @Column(name = "SEQQ")
    private Long seqq;

    /**
     * 结束scn号
     */
    @Column(name = "SCNZ")
    private BigDecimal scnz;

    /**
     * 结束sequence号
     */
    @Column(name = "SEQZ")
    private Long seqz;

    /**
     * 上一个文件名
     */
    @Column(name = "SYWJM")
    private String sywjm;

    /**
     * 下一个文件名
     */
    @Column(name = "XYWJM")
    private String xywjm;

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
     * 获取Insert数据量
     *
     * @return SJLINSERT - Insert数据量
     */
    public BigDecimal getSjlinsert() {
        return sjlinsert;
    }

    /**
     * 设置Insert数据量
     *
     * @param sjlinsert Insert数据量
     */
    public void setSjlinsert(BigDecimal sjlinsert) {
        this.sjlinsert = sjlinsert;
    }

    /**
     * 获取Update数据量
     *
     * @return SJLUPDATE - Update数据量
     */
    public BigDecimal getSjlupdate() {
        return sjlupdate;
    }

    /**
     * 设置Update数据量
     *
     * @param sjlupdate Update数据量
     */
    public void setSjlupdate(BigDecimal sjlupdate) {
        this.sjlupdate = sjlupdate;
    }

    /**
     * 获取Delete数据量
     *
     * @return SJLDELETE - Delete数据量
     */
    public BigDecimal getSjldelete() {
        return sjldelete;
    }

    /**
     * 设置Delete数据量
     *
     * @param sjldelete Delete数据量
     */
    public void setSjldelete(BigDecimal sjldelete) {
        this.sjldelete = sjldelete;
    }

    /**
     * 获取起始scn号
     *
     * @return SCNQ - 起始scn号
     */
    public BigDecimal getScnq() {
        return scnq;
    }

    /**
     * 设置起始scn号
     *
     * @param scnq 起始scn号
     */
    public void setScnq(BigDecimal scnq) {
        this.scnq = scnq;
    }

    /**
     * 获取起始sequence号
     *
     * @return SEQQ - 起始sequence号
     */
    public Long getSeqq() {
        return seqq;
    }

    /**
     * 设置起始sequence号
     *
     * @param seqq 起始sequence号
     */
    public void setSeqq(Long seqq) {
        this.seqq = seqq;
    }

    /**
     * 获取结束scn号
     *
     * @return SCNZ - 结束scn号
     */
    public BigDecimal getScnz() {
        return scnz;
    }

    /**
     * 设置结束scn号
     *
     * @param scnz 结束scn号
     */
    public void setScnz(BigDecimal scnz) {
        this.scnz = scnz;
    }

    /**
     * 获取结束sequence号
     *
     * @return SEQZ - 结束sequence号
     */
    public Long getSeqz() {
        return seqz;
    }

    /**
     * 设置结束sequence号
     *
     * @param seqz 结束sequence号
     */
    public void setSeqz(Long seqz) {
        this.seqz = seqz;
    }

    /**
     * 获取上一个文件名
     *
     * @return SYWJM - 上一个文件名
     */
    public String getSywjm() {
        return sywjm;
    }

    /**
     * 设置上一个文件名
     *
     * @param sywjm 上一个文件名
     */
    public void setSywjm(String sywjm) {
        this.sywjm = sywjm == null ? null : sywjm.trim();
    }

    /**
     * 获取下一个文件名
     *
     * @return XYWJM - 下一个文件名
     */
    public String getXywjm() {
        return xywjm;
    }

    /**
     * 设置下一个文件名
     *
     * @param xywjm 下一个文件名
     */
    public void setXywjm(String xywjm) {
        this.xywjm = xywjm == null ? null : xywjm.trim();
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