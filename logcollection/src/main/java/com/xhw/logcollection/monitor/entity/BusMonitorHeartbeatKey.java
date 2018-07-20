package com.xhw.logcollection.monitor.entity;

import javax.persistence.*;

@Table(name = "BUS_MONITOR_HEARTBEAT")
public class BusMonitorHeartbeatKey {
    /**
     * 备案编号
     */
    @Id
    @Column(name = "BABH")
    private String babh;

    /**
     * 上报周期，YYYYMMDDHH24Mi
     */
    @Id
    @Column(name = "SBZQ")
    private String sbzq;

    /**
     * 获取备案编号
     *
     * @return BABH - 备案编号
     */
    public String getBabh() {
        return babh;
    }

    /**
     * 设置备案编号
     *
     * @param babh 备案编号
     */
    public void setBabh(String babh) {
        this.babh = babh == null ? null : babh.trim();
    }

    /**
     * 获取上报周期，YYYYMMDDHH24Mi
     *
     * @return SBZQ - 上报周期，YYYYMMDDHH24Mi
     */
    public String getSbzq() {
        return sbzq;
    }

    /**
     * 设置上报周期，YYYYMMDDHH24Mi
     *
     * @param sbzq 上报周期，YYYYMMDDHH24Mi
     */
    public void setSbzq(String sbzq) {
        this.sbzq = sbzq == null ? null : sbzq.trim();
    }
}