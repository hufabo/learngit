package com.xhw.logcollection.job.ws;

import com.xhw.logcollection.monitor.entity.BusMonitorHeartbeat;

/**
 * 心跳状态上报webservice服务接口类
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-28
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public interface ReportHeartbeatWsServ {
    public boolean reportHeartbeat(BusMonitorHeartbeat bean) throws Exception;
}
