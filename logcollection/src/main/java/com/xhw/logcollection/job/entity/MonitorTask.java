package com.xhw.logcollection.job.entity;

import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "BUS_MONITOR_TASK")
public class MonitorTask {
    /**
     * 任务编号
     */
    @Id
    @Column(name = "RWBH")
    private String rwbh;

    /**
     * 任务名称
     */
    @Column(name = "RWMC")
    private String rwmc;

    /**
     * 是否启用：1=启用，2=禁用
     */
    @Column(name = "SFQY")
    private String sfqy;

    /**
     * 运行状态：1=停止 2=运行 3=挂起
     */
    @Column(name = "YXZT")
    private String yxzt;

    /**
     * 是否自动：1=自动，2=手动
     */
    @Column(name = "SFZD")
    private String sfzd;

    /**
     * 任务心跳上报频率
     */
    @Column(name = "RWXTSBPL")
    private BigDecimal rwxtsbpl;

    /**
     * 任务最大并发数量，默认1
     */
    @Column(name = "RWZDBFSL")
    private BigDecimal rwzdbfsl;

    /**
     * 获取任务编号
     *
     * @return RWBH - 任务编号
     */
    public String getRwbh() {
        return rwbh;
    }

    /**
     * 设置任务编号
     *
     * @param rwbh 任务编号
     */
    public void setRwbh(String rwbh) {
        this.rwbh = rwbh == null ? null : rwbh.trim();
    }

    /**
     * 获取任务名称
     *
     * @return RWMC - 任务名称
     */
    public String getRwmc() {
        return rwmc;
    }

    /**
     * 设置任务名称
     *
     * @param rwmc 任务名称
     */
    public void setRwmc(String rwmc) {
        this.rwmc = rwmc == null ? null : rwmc.trim();
    }

    /**
     * 获取是否启用：1=启用，2=禁用
     *
     * @return SFQY - 是否启用：1=启用，2=禁用
     */
    public String getSfqy() {
        return sfqy;
    }

    /**
     * 设置是否启用：1=启用，2=禁用
     *
     * @param sfqy 是否启用：1=启用，2=禁用
     */
    public void setSfqy(String sfqy) {
        this.sfqy = sfqy == null ? null : sfqy.trim();
    }

    /**
     * 获取任务心跳上报频率
     *
     * @return RWXTSBPL - 任务心跳上报频率
     */
    public BigDecimal getRwxtsbpl() {
        return rwxtsbpl;
    }

    /**
     * 设置任务心跳上报频率
     *
     * @param rwxtsbpl 任务心跳上报频率
     */
    public void setRwxtsbpl(BigDecimal rwxtsbpl) {
        this.rwxtsbpl = rwxtsbpl;
    }

    /**
     * 获取任务最大并发数量，默认1
     *
     * @return RWZDBFSL - 任务最大并发数量，默认1
     */
    public BigDecimal getRwzdbfsl() {
        return rwzdbfsl;
    }

    /**
     * 设置任务最大并发数量，默认1
     *
     * @param rwzdbfsl 任务最大并发数量，默认1
     */
    public void setRwzdbfsl(BigDecimal rwzdbfsl) {
        this.rwzdbfsl = rwzdbfsl;
    }

    public String getYxzt() {
        return yxzt;
    }

    public void setYxzt(String yxzt) {
        this.yxzt = yxzt;
    }

    public String getSfzd() {
        return sfzd;
    }

    public void setSfzd(String sfzd) {
        this.sfzd = sfzd;
    }
}