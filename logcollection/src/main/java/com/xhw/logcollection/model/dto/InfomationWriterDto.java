package com.xhw.logcollection.model.dto;

import com.xhw.logcollection.monitor.entity.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @Author 孔纲
 * @Date 2018/3/8
 */
@XmlRootElement(name = "root")
public class InfomationWriterDto {

    private List<BusMonitorHeartbeat> heartbeats;

    private List<BusMonitorRunlog> runlogs;

    private List<BusStockDealStatus> stockDealStatuses;

    private List<BusStockBreakPointInfo> stockBreakPointInfos;

    private List<BusIncrementBreakPointInfo> incrementBreakPointInfos;

    private List<BusStockFileInfo> stockFileInfos;

    private List<BusIncrementFileInfo> incrementFileInfos;

    private List<BusAuditDdl> auditDdls;

    @XmlElement(name = "drvexam")
    public List<BusMonitorHeartbeat> getHeartbeats() {
        return heartbeats;
    }

    public void setHeartbeats(List<BusMonitorHeartbeat> heartbeats) {
        this.heartbeats = heartbeats;
    }

    @XmlElement(name = "rdsServerStatusWrite")
    public List<BusMonitorRunlog> getRunlogs() {
        return runlogs;
    }

    public void setRunlogs(List<BusMonitorRunlog> runlogs) {
        this.runlogs = runlogs;
    }

    @XmlElement(name = "drvexam")
    public List<BusStockDealStatus> getStockDealStatuses() {
        return stockDealStatuses;
    }

    public void setStockDealStatuses(List<BusStockDealStatus> stockDealStatuses) {
        this.stockDealStatuses = stockDealStatuses;
    }

    @XmlElement(name = "drvexam")
    public List<BusStockBreakPointInfo> getStockBreakPointInfos() {
        return stockBreakPointInfos;
    }

    public void setStockBreakPointInfos(List<BusStockBreakPointInfo> stockBreakPointInfos) {
        this.stockBreakPointInfos = stockBreakPointInfos;
    }

    @XmlElement(name = "drvexam")
    public List<BusIncrementBreakPointInfo> getIncrementBreakPointInfos() {
        return incrementBreakPointInfos;
    }

    public void setIncrementBreakPointInfos(List<BusIncrementBreakPointInfo> incrementBreakPointInfos) {
        this.incrementBreakPointInfos = incrementBreakPointInfos;
    }

    @XmlElement(name = "drvexam")
    public List<BusStockFileInfo> getStockFileInfos() {
        return stockFileInfos;
    }

    public void setStockFileInfos(List<BusStockFileInfo> stockFileInfos) {
        this.stockFileInfos = stockFileInfos;
    }

    @XmlElement(name = "drvexam")
    public List<BusIncrementFileInfo> getIncrementFileInfos() {
        return incrementFileInfos;
    }

    public void setIncrementFileInfos(List<BusIncrementFileInfo> incrementFileInfos) {
        this.incrementFileInfos = incrementFileInfos;
    }

    public List<BusAuditDdl> getAuditDdls() {
        return auditDdls;
    }

    public void setAuditDdls(List<BusAuditDdl> auditDdls) {
        this.auditDdls = auditDdls;
    }
}
