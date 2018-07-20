package com.xhw.logcollection.job.service;

import com.github.pagehelper.PageInfo;
import com.xhw.logcollection.job.entity.MonitorTaskRunlog;
import com.xhw.logcollection.job.entity.MonitorWarn;
import com.xhw.logcollection.model.dto.MonitorWarnDto;

import java.util.List;
import java.util.Map;

/**
 * 任务运行记录服务接口
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-26
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public interface MonitorTaskRunlogServ {

    public void save(MonitorTaskRunlog bean) throws Exception;

    public void update(MonitorTaskRunlog bean) throws Exception;

    /**
     * 按照时间倒序排列
     * @author konggang
     * @date 2018/3/6 10:14
     */
    List<MonitorWarnDto> getTaskRunLogList(Map<String,Object> paraMap);

    /**
     * 根据条件返回任务运行记录
     * @param paraMap
     * @return
     */
    PageInfo<MonitorTaskRunlog> listByCondition(Map<String,Object> paraMap);
}
