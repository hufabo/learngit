package com.xhw.logcollection.job.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "BUS_MONITOR_TASK_RUNLOG")
public class MonitorTaskRunlog {
    /**
     * 记录ID
     */
    @Id
    @Column(name = "RUNID")
    private BigDecimal runid;

    @Column(name = "RWBH")
    private String rwbh;

    /**
     * 开始时间
     */
    @Column(name = "KSSJ")
    private Date kssj;

    /**
     * 结束时间
     */
    @Column(name = "JSSJ")
    private Date jssj;

    /**
     * 运行结果，1=成功/2=失败
     */
    @Column(name = "YXJG")
    private String yxjg;

    /**
     * 运行时长，单位为秒
     */
    @Column(name = "YXSC")
    private BigDecimal yxsc;

    /**
     * 运行时长，单位为秒
     */
    @Column(name = "warnid")
    @Transient
    private String warnid;

    /**
     * 任务信息
     */
    @Column(name = "taskinfo")
    private String taskinfo;

    /**
     * 获取记录ID
     *
     * @return RUNID - 记录ID
     */
    public BigDecimal getRunid() {
        return runid;
    }

    /**
     * 设置记录ID
     *
     * @param runid 记录ID
     */
    public void setRunid(BigDecimal runid) {
        this.runid = runid;
    }

    /**
     * @return RWBH
     */
    public String getRwbh() {
        return rwbh;
    }

    /**
     * @param rwbh
     */
    public void setRwbh(String rwbh) {
        this.rwbh = rwbh == null ? null : rwbh.trim();
    }

    /**
     * 获取开始时间
     *
     * @return KSSJ - 开始时间
     */
    public Date getKssj() {
        return kssj;
    }

    /**
     * 设置开始时间
     *
     * @param kssj 开始时间
     */
    public void setKssj(Date kssj) {
        this.kssj = kssj;
    }

    /**
     * 获取结束时间
     *
     * @return JSSJ - 结束时间
     */
    public Date getJssj() {
        return jssj;
    }

    /**
     * 设置结束时间
     *
     * @param jssj 结束时间
     */
    public void setJssj(Date jssj) {
        this.jssj = jssj;
    }

    /**
     * 获取运行结果，1=成功/2=失败
     *
     * @return YXJG - 运行结果，1=成功/2=失败
     */
    public String getYxjg() {
        return yxjg;
    }

    /**
     * 设置运行结果，1=成功/2=失败
     *
     * @param yxjg 运行结果，1=成功/2=失败
     */
    public void setYxjg(String yxjg) {
        this.yxjg = yxjg == null ? null : yxjg.trim();
    }

    /**
     * 获取运行时长，单位为秒
     *
     * @return YXSC - 运行时长，单位为秒
     */
    public BigDecimal getYxsc() {
        return yxsc;
    }

    /**
     * 设置运行时长，单位为秒
     *
     * @param yxsc 运行时长，单位为秒
     */
    public void setYxsc(BigDecimal yxsc) {
        this.yxsc = yxsc;
    }

    public String getWarnid() {
        return warnid;
    }

    public void setWarnid(String warnid) {
        this.warnid = warnid;
    }

    public String getTaskinfo() {
        return taskinfo;
    }

    public void setTaskinfo(String taskinfo) {
        this.taskinfo = taskinfo;
    }
}