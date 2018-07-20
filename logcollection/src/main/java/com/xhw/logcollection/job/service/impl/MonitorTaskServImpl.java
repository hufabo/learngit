package com.xhw.logcollection.job.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.job.entity.MonitorTask;
import com.xhw.logcollection.job.mapper.MonitorTaskRunlogMapper;
import com.xhw.logcollection.job.quartz.SchedulerStart;
import com.xhw.logcollection.job.mapper.MonitorTaskMapper;
import com.xhw.logcollection.job.service.MonitorTaskServ;
import com.xhw.logcollection.job.thread.ctrl.*;
import com.xhw.logcollection.job.thread.tasks.*;
import com.xhw.logcollection.model.dto.MonitorTaskDto;
import com.xhw.logcollection.util.Constant;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 监控任务业务层实现类
 * @author 孔纲
 * @date 2018/3/5
 */
@Service
public class MonitorTaskServImpl implements MonitorTaskServ {

    @Autowired
    private MonitorTaskMapper mapper;

    @Autowired
    private MonitorTaskRunlogMapper monitorTaskRunlogMapper;

    @Autowired
    private SchedulerStart schedulerStart;

    /**
     * 根据条件获取列表
     * @param paraMap
     * @return
     */
    @Override
    public PageInfo<MonitorTaskDto> listByCondition(Map<String, Object> paraMap) {
        if(paraMap.get("curentPage")!=null && !paraMap.get("curentPage").toString().equals("")){
            PageHelper.startPage(Integer.valueOf(paraMap.get("curentPage").toString()),Integer.valueOf(paraMap.get("pageSize").toString()));
        }
        List<MonitorTaskDto> list = mapper.listByCondition(paraMap);
        PageInfo<MonitorTaskDto> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public PageInfo<MonitorTask> queryList(Map<String, Object> paraMap) {
        if(paraMap.get("curentPage")!=null && !paraMap.get("curentPage").toString().equals("")){
            PageHelper.startPage(Integer.valueOf(paraMap.get("curentPage").toString()),Integer.valueOf(paraMap.get("pageSize").toString()));
        }
        List<MonitorTask> list = mapper.queryList(paraMap);
        PageInfo<MonitorTask> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public MonitorTask selectOne(MonitorTask task) {
        return mapper.selectOne(task);
    }

    @Override
    public Long getTaskCountByCondition(Map<String, Object> paraMap) {
        return monitorTaskRunlogMapper.getTaskCountByCondition(paraMap);
    }

    @Override
    public void startTaskByTaskId(String taskId) {
        int status = TaskStatus.getYXZT(taskId);
        if(status == 1){
            if(Constant.TASKID_UPDATE_STRATEGY_TASK.equals(taskId)){ // 03
                ContextBean.commonTaskThreadPool.submit(new UpdateStrategyTask());
            }else if(Constant.TASKID_STOCK_SPLIT_TASK.equals(taskId)){ // 04
                //初始化
                StockSplitCtrlTask.init();
                ContextBean.commonTaskThreadPool.submit(new StockSplitCtrlTask());
            }else if(Constant.TASKID_STOCK_EXECUTE_TASK.equals(taskId)){ // 05
                StockExecuteCtrlTask.init();
                ContextBean.commonTaskThreadPool.submit(new StockExecuteCtrlTask());
            }else if(Constant.TASKID_FILE_PUT_TASK.equals(taskId)){ // 01
                //初始化
                FilePutCtrlTask.init();
                ContextBean.commonTaskThreadPool.submit(new FilePutCtrlTask());
            }else if(Constant.TASKID_FILE_RE_PUT_TASK.equals(taskId)){ // 02
                ContextBean.commonTaskThreadPool.submit(new FileRePutTask());
            }else if(Constant.TASKID_INCR_INTEGRATED_TASK.equals(taskId)){ // 13
                IncrIntegratedCtrlTask.init();
                ContextBean.commonTaskThreadPool.submit(new IncrIntegratedCtrlTask());
            }else if(Constant.TASKID_HTTP_INTERACTION_TASK.equals(taskId)){ // 09
                ContextBean.commonTaskThreadPool.submit(new HttpInteractionTask());
            }else if(Constant.TASKID_FILE_RESULT_STATUS_TASK.equals(taskId)){ // 08
                ContextBean.commonTaskThreadPool.submit(new FileResultStatusTask());
            }else if(Constant.TASKID_REPORT_HEARTBEAT_TASK.equals(taskId)){ // 10
                ContextBean.commonTaskThreadPool.submit(new ReportHeartbeatTask());
            }else if(Constant.TASKID_RUNLOG_TASK.equals(taskId)){ // 11
                RunlogCtrlTask.init();
                ContextBean.commonTaskThreadPool.submit(new RunlogCtrlTask());
            }else if(Constant.TASKID_MONITOR_STAT_TASK.equals(taskId)){ // 12
                MonitorStatCtrlTask.init();
                ContextBean.commonTaskThreadPool.submit(new MonitorStatCtrlTask());
            }
        }else{
            throw new RuntimeException("任务运行中...");
        }
    }

    @Override
    public void pauseJobByTaskId(String taskId) throws Exception {
//        schedulerStart.pauseJob(taskId);
        MonitorTask task = new MonitorTask();
        task.setRwbh(taskId);
        task.setSfzd("2");
        mapper.updateByPrimaryKeySelective(task);
    }

    @Override
    public void resumeJobByTaskId(String taskId) throws SchedulerException {
//        schedulerStart.resumeJob(taskId);
        MonitorTask task = new MonitorTask();
        task.setRwbh(taskId);
        task.setSfzd("1");
        mapper.updateByPrimaryKeySelective(task);
    }

    @Override
    public void refreshJobTriggerByTaskId(String taskId, String cronExper) throws Exception {
        String triggerName = "trigger_" + taskId;
        schedulerStart.changeTrigger(triggerName, cronExper);
    }
}
