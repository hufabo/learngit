package com.xhw.logcollection.monitor.service;

import com.github.pagehelper.PageInfo;
import com.xhw.logcollection.monitor.entity.BusAuditDdl;
import com.xhw.logcollection.monitor.entity.BusMonitorRunlog;

import java.util.List;
import java.util.Map;

/**
 * @author 孔纲
 * @date 2018/3/2
 */
public interface BusMonitorRunlogServ {

    /**
     * 根据条件获取列表
     * @author konggang
     * @date 2018/3/2 17:39
     */
    PageInfo<BusMonitorRunlog> listByCondition(Map<String,Object> paraMap);

    /**
     * 查询软件运行错误记录
     * @author konggang
     * @date 2018/3/21 15:52
     */
    List<BusMonitorRunlog> queryErrorLogs(Map<String,Object> paraMap);

    /**
     * 不存在则新增，存在则更新
     * @author konggang
     * @date 2018/4/18 16:35
     */
    void save(BusMonitorRunlog log);
}
