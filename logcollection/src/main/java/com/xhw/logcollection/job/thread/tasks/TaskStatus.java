package com.xhw.logcollection.job.thread.tasks;

import com.xhw.logcollection.job.entity.MonitorTask;
import com.xhw.logcollection.job.mapper.MonitorTaskMapper;
import com.xhw.logcollection.util.SpringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用于初始化和记录各任务运行状态
 * @Author 孔纲
 * @Date 2018/4/17
 */
public class TaskStatus {

    /**
     * 任务状态初始化
     * @author konggang
     * @date 2018/4/17 17:03
     */
    public static void initTaskStatus(){
        MonitorTaskMapper monitorTaskMapper = SpringUtil.getBean(MonitorTaskMapper.class);
        monitorTaskMapper.initTaskStatus();
    }

    /**
     * 任务运行
     * @author konggang
     * @date 2018/4/17 17:03
     */
    public static void statusRunning(String taskId) {
        MonitorTask monitorTask = new MonitorTask();
        monitorTask.setRwbh(taskId);
        monitorTask.setYxzt("2");
        MonitorTaskMapper monitorTaskMapper = SpringUtil.getBean(MonitorTaskMapper.class);
        monitorTaskMapper.updateByPrimaryKeySelective(monitorTask);
    }

    /**
     * 任务停止
     * @author konggang
     * @date 2018/4/17 17:04
     */
    public static void statusStopped(String taskId) {
        MonitorTask monitorTask = new MonitorTask();
        monitorTask.setRwbh(taskId);
        monitorTask.setYxzt("1");
        MonitorTaskMapper monitorTaskMapper = SpringUtil.getBean(MonitorTaskMapper.class);
        monitorTaskMapper.updateByPrimaryKeySelective(monitorTask);
    }

    /**
     * 任务挂起
     * @author konggang
     * @date 2018/4/17 17:05
     */
    public static void statusSuspend(String taskId) {
        MonitorTask monitorTask = new MonitorTask();
        monitorTask.setRwbh(taskId);
        monitorTask.setYxzt("3");
        MonitorTaskMapper monitorTaskMapper = SpringUtil.getBean(MonitorTaskMapper.class);
        monitorTaskMapper.updateByPrimaryKeySelective(monitorTask);
    }

    /**
     * 获取任务状态
     * @author konggang
     * @date 2018/4/17 17:05
     */
    public static int getYXZT(String taskId){
        MonitorTaskMapper monitorTaskMapper = SpringUtil.getBean(MonitorTaskMapper.class);
        MonitorTask monitorTask = monitorTaskMapper.selectByPrimaryKey(taskId);
        String yxzt = monitorTask.getYxzt();
        if(yxzt == null){
           return 0;
        }else{
            return Integer.valueOf(yxzt);
        }
    }


    public static int getSFZD(String taskId){
        MonitorTaskMapper monitorTaskMapper = SpringUtil.getBean(MonitorTaskMapper.class);
        MonitorTask monitorTask = monitorTaskMapper.selectByPrimaryKey(taskId);
        String sfzd = monitorTask.getSfzd();
        if(sfzd == null){
            return 1;
        }else{
            return Integer.valueOf(sfzd);
        }
    }


}
