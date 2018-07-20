package com.xhw.logcollection.model.dto;

import com.xhw.logcollection.job.entity.MonitorWarn;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 异常告警传输对象
 * @author 孔纲
 * @date 2018/3/5
 */
public class MonitorWarnDto extends MonitorWarn {

    /**
     * 任务编号
     */
    private String rwbh;

    /**
     * 任务名称
     */
    private String rwmc;

    /**
     * 是否启用：1=启用，2=禁用
     */
    private String sfqy;

    /**
     * 任务心跳上报频率
     */
    private BigDecimal rwxtsbpl;

    /**
     * 任务最大并发数量，默认1
     */
    private BigDecimal rwzdbfsl;

    /**
     * 开始时间
     */
    private Date kssj;

    /**
     * 结束时间
     */
    private Date jssj;

    /**
     * 运行结果，1=成功/2=失败
     */
    private String yxjg;

    /**
     * 运行时长，单位为秒
     */
    private BigDecimal yxsc;

    public String getRwbh() {
        return rwbh;
    }

    public void setRwbh(String rwbh) {
        this.rwbh = rwbh;
    }

    public String getRwmc() {
        return rwmc;
    }

    public void setRwmc(String rwmc) {
        this.rwmc = rwmc;
    }

    public String getSfqy() {
        return sfqy;
    }

    public void setSfqy(String sfqy) {
        this.sfqy = sfqy;
    }

    public BigDecimal getRwxtsbpl() {
        return rwxtsbpl;
    }

    public void setRwxtsbpl(BigDecimal rwxtsbpl) {
        this.rwxtsbpl = rwxtsbpl;
    }

    public BigDecimal getRwzdbfsl() {
        return rwzdbfsl;
    }

    public void setRwzdbfsl(BigDecimal rwzdbfsl) {
        this.rwzdbfsl = rwzdbfsl;
    }

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

    public BigDecimal getYxsc() {
        return yxsc;
    }

    public void setYxsc(BigDecimal yxsc) {
        this.yxsc = yxsc;
    }
}
