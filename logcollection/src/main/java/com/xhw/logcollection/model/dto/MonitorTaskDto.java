package com.xhw.logcollection.model.dto;

import com.xhw.logcollection.job.entity.MonitorTask;

import java.util.Date;

/**
 * 监控任务数据传输封装对象
 * @author 孔纲
 * @date 2018/3/5
 */
public class MonitorTaskDto extends MonitorTask {

    /*
    开始时间
     */
    private Date kssj;
    /*
    结束时间
     */
    private Date jssj;
    /*
    运行结果
     */
    private String yxjg;
    /*
    运行时长
     */
    private long yxsc;
    /*
    错误次数
     */
    private int cwcs;
    /*
    错误类型
     */
    private String cwlx;
    /*
    错误CODE
     */
    private String cwcode;

    public Date getKssj() {
        return kssj;
    }

    public void setKssj(Date kssj) {
        this.kssj = kssj;
    }

    public Date getJssj() {
        return jssj;
    }

    public void setJssj(Date jssj) {
        this.jssj = jssj;
    }

    public String getYxjg() {
        return yxjg;
    }

    public void setYxjg(String yxjg) {
        this.yxjg = yxjg;
    }

    public long getYxsc() {
        return yxsc;
    }

    public void setYxsc(long yxsc) {
        this.yxsc = yxsc;
    }

    public int getCwcs() {
        return cwcs;
    }

    public void setCwcs(int cwcs) {
        this.cwcs = cwcs;
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
}
