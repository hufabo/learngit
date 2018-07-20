package com.xhw.logcollection.job.service;

/**
 * 心跳状态上报服务接口类
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-28
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public interface ReportHeartbeatServ {
    //上报心跳
    public void reportHeartbeat() throws Exception;
}
