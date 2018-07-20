package com.xhw.logcollection.job.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 异常报警
 * @author 孔纲
 * @date 2018/3/2
 */
@Table(name = "BUS_MONITOR_WARN")
public class MonitorWarn {
    /**
     * 运行记录ID
     */
    @Id
    @Column(name = "runid")
    private long runid;

    /**
     * 记录ID
     */
    @Id
    @Column(name = "warnid")
    private long warnid;

    /**
     * 错误类型，ERROR、 WARN
     */
    @Column(name = "cwlx")
    private String cwlx;

    /**
     * 错误CODE，（CODE类型待补充）
     */
    @Column(name = "cwcode")
    private String cwcode;

    /**
     * 错误时间
     */
    @Column(name = "cwsj")
    private Date cwsj;

    /**
     * 错误摘要
     */
    @Column(name = "cwzy")
    private String cwzy;

    /**
     * 错误详细内容
     */
    @Column(name = "cwxxnr")
    private String cwxxnr;

    /**
     * 是否告警：1=是，2=否
     */
    @Column(name = "sfgj")
    private String sfgj;

    /**
     * 是否挂起：1=是，2=否
     */
    @Column(name = "sfgq")
    private String sfgq;

    /**
     * 是否已处理：1=是，2=否
     */
    @Column(name = "sfycl")
    private String sfycl;

    /**
     * 处理时间
     */
    @Column(name = "clsj")
    private Date clsj;

    /**
     * 处理备注
     */
    @Column(name = "clbz")
    private String clbz;

    public MonitorWarn() {
    }

    public long getRunid() {
        return runid;
    }

    public void setRunid(long runid) {
        this.runid = runid;
    }

    public long getWarnid() {
        return warnid;
    }

    public void setWarnid(long warnid) {
        this.warnid = warnid;
    }

    public String getCwlx() {
        return cwlx;
    }

    public void setCwlx(String cwlx) {
        this.cwlx = cwlx;
    }

    public String getCwcode() {
        return cwcode;
    }

    public void setCwcode(String cwcode) {
        this.cwcode = cwcode;
    }

    public Date getCwsj() {
        return cwsj;
    }

    public void setCwsj(Date cwsj) {
        this.cwsj = cwsj;
    }

    public String getCwzy() {
        return cwzy;
    }

    public void setCwzy(String cwzy) {
        this.cwzy = cwzy;
    }

    public String getCwxxnr() {
        return cwxxnr;
    }

    public void setCwxxnr(String cwxxnr) {
        this.cwxxnr = cwxxnr;
    }

    public String getSfgj() {
        return sfgj;
    }

    public void setSfgj(String sfgj) {
        this.sfgj = sfgj;
    }

    public String getSfgq() {
        return sfgq;
    }

    public void setSfgq(String sfgq) {
        this.sfgq = sfgq;
    }

    public String getSfycl() {
        return sfycl;
    }

    public void setSfycl(String sfycl) {
        this.sfycl = sfycl;
    }

    public Date getClsj() {
        return clsj;
    }

    public void setClsj(Date clsj) {
        this.clsj = clsj;
    }

    public String getClbz() {
        return clbz;
    }

    public void setClbz(String clbz) {
        this.clbz = clbz;
    }

}
