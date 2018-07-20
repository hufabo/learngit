package com.xhw.logcollection.job.service;

import com.github.pagehelper.PageInfo;
import com.xhw.logcollection.job.entity.MonitorTask;
import com.xhw.logcollection.model.dto.MonitorTaskDto;
import org.quartz.SchedulerException;

import java.util.Map;

/**
 * 监控任务业务层
 * @author 孔纲
 * @date 2018/3/5
 */
public interface MonitorTaskServ {

    /**
     * 根据条件获取列表
     * @author konggang
     * @date 2018/3/2 17:39
     */
    PageInfo<MonitorTaskDto> listByCondition(Map<String,Object> paraMap);

    PageInfo<MonitorTask> queryList(Map<String,Object> paraMap);

    MonitorTask selectOne(MonitorTask task);

    /**
     * 根据条件获取任务数
     * @author konggang
     * @date 2018/3/6 13:40
     */
    Long getTaskCountByCondition(Map<String,Object> paraMap);

    /**
     * 根据任务ID启动指定任务线程
     * @author konggang
     * @date 2018/3/21 10:42
     */
    void startTaskByTaskId(String taskId);

    /**
     * 暂停任务
     * @author konggang
     * @date 2018/3/30 14:00
     */
    void pauseJobByTaskId(String taskId) throws Exception;

    /**
     * 恢复任务
     * @author konggang
     * @date 2018/3/30 14:00
     */
    void resumeJobByTaskId(String taskId) throws SchedulerException;

    /**
     * 刷新任务时间
     * @author konggang
     * @date 2018/3/30 14:35
     */
    void refreshJobTriggerByTaskId(String taskId, String cronExper) throws Exception ;
}
