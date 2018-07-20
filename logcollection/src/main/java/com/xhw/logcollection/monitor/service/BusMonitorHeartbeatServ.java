package com.xhw.logcollection.monitor.service;

import com.github.pagehelper.PageInfo;
import com.xhw.logcollection.monitor.entity.BusMonitorHeartbeat;

import java.util.Map;

/**
 * 心跳状态上报服务接口类
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-28
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public interface BusMonitorHeartbeatServ {
    /**
     * 保存
     * @param bean
     * @throws Exception
     */
    void save(BusMonitorHeartbeat bean) throws Exception;

    /**
     * 根据开始结束时间获取心跳报表数据
     * @author konggang
     * @date 2018/3/6 17:16
     */
    Map<String, Object> queryHeartbeatReportDatas(Map<String, Object> params);
}
