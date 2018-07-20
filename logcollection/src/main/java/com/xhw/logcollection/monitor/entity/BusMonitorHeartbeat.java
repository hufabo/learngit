package com.xhw.logcollection.monitor.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "BUS_MONITOR_HEARTBEAT")
public class BusMonitorHeartbeat extends BusMonitorHeartbeatKey {
    /**
     * cpu使用率
     */
    @Column(name = "CPUSYL")
    private Short cpusyl;

    /**
     * 自增主键
     */
    @Column(name = "id")
    private String id;

    /**
     * 内存使用率
     */
    @Column(name = "NCSYL")
    private Short ncsyl;

    /**
     * 磁盘使用率
     */
    @Column(name = "CPSYL")
    private Short cpsyl;

    /**
     * Linux操作系统中，top命令中，5分钟系统负载值
     */
    @Column(name = "XTFZ")
    private Short xtfz;

    /**
     * 更新时间，精确到秒
     */
    @Column(name = "GXSJ")
    private Date gxsj;

    /**
     * 获取cpu使用率
     *
     * @return CPUSYL - cpu使用率
     */
    public Short getCpusyl() {
        return cpusyl;
    }

    /**
     * 设置cpu使用率
     *
     * @param cpusyl cpu使用率
     */
    public void setCpusyl(Short cpusyl) {
        this.cpusyl = cpusyl;
    }

    /**
     * 获取内存使用率
     *
     * @return NCSYL - 内存使用率
     */
    public Short getNcsyl() {
        return ncsyl;
    }

    /**
     * 设置内存使用率
     *
     * @param ncsyl 内存使用率
     */
    public void setNcsyl(Short ncsyl) {
        this.ncsyl = ncsyl;
    }

    /**
     * 获取磁盘使用率
     *
     * @return CPSYL - 磁盘使用率
     */
    public Short getCpsyl() {
        return cpsyl;
    }

    /**
     * 设置磁盘使用率
     *
     * @param cpsyl 磁盘使用率
     */
    public void setCpsyl(Short cpsyl) {
        this.cpsyl = cpsyl;
    }

    /**
     * 获取Linux操作系统中，top命令中，5分钟系统负载值
     *
     * @return XTFZ - Linux操作系统中，top命令中，5分钟系统负载值
     */
    public Short getXtfz() {
        return xtfz;
    }

    /**
     * 设置Linux操作系统中，top命令中，5分钟系统负载值
     *
     * @param xtfz Linux操作系统中，top命令中，5分钟系统负载值
     */
    public void setXtfz(Short xtfz) {
        this.xtfz = xtfz;
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
        return "BusMonitorHeartbeat{" +
                "cpusyl=" + cpusyl +
                ", ncsyl=" + ncsyl +
                ", cpsyl=" + cpsyl +
                ", xtfz=" + xtfz +
                ", gxsj=" + gxsj +
                '}';
    }
}