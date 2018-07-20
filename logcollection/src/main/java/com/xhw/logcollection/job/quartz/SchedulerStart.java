package com.xhw.logcollection.job.quartz;

import com.xhw.logcollection.job.entity.ConfigurationBean;
import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.util.LoadGlobalPropertiesUtil;
import org.quartz.*;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;

/**
 * 定时任务控制器
 * @Author 孔纲
 * @Date 2018/3/30
 */
@Component
public class SchedulerStart {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    static {
        //初始化后端任务启动参数
        if(ContextBean.configuration == null){
            ConfigurationBean cfg = new ConfigurationBean();
            // 从全局配置文件读取 线程间隔时间 配置
            cfg.setPatternFilePutDeamon(LoadGlobalPropertiesUtil.getProperty("task.schedule.pattern.fileput"));
            cfg.setPatternFileRePutDeamon(LoadGlobalPropertiesUtil.getProperty("task.schedule.pattern.filereput"));
            cfg.setPatternFileResultStatusDeamon(LoadGlobalPropertiesUtil.getProperty("task.schedule.pattern.fileresultstatus"));
            cfg.setPatternHttpInteractionDeamon(LoadGlobalPropertiesUtil.getProperty("task.schedule.pattern.http"));
            cfg.setPatternIncrExecuteDeamon(LoadGlobalPropertiesUtil.getProperty("task.schedule.pattern.incr.execute"));
            cfg.setPatternIncrSplitDeamon(LoadGlobalPropertiesUtil.getProperty("task.schedule.pattern.incr.split"));
            cfg.setPatternIncrGetSourceFileDeamon(LoadGlobalPropertiesUtil.getProperty("task.schedule.pattern.incr.getfile"));
            cfg.setPatternReportHeartbeatDeamon(LoadGlobalPropertiesUtil.getProperty("task.schedule.pattern.heartbeat"));
            cfg.setPatternStockExecuteDeamon(LoadGlobalPropertiesUtil.getProperty("task.schedule.pattern.stock.execute"));
            cfg.setPatternStockSplitDeamon(LoadGlobalPropertiesUtil.getProperty("task.schedule.pattern.stock.split"));
            cfg.setPatternUpdateStrategyDeamon(LoadGlobalPropertiesUtil.getProperty("task.schedule.pattern.updatestrategy"));
            cfg.setPatternRunlogDeamon(LoadGlobalPropertiesUtil.getProperty("task.schedule.pattern.runlog"));
            cfg.setPatternMonitorStatDeamon(LoadGlobalPropertiesUtil.getProperty("task.schedule.pattern.monitor.stat"));
            cfg.setPatternIncrIntegratedDeamon(LoadGlobalPropertiesUtil.getProperty("task.schedule.pattern.incr.integrated"));
            ContextBean.configuration = cfg ;
        }

        //初始化公共线程池
        if(ContextBean.commonTaskThreadPool == null){
            ContextBean.commonTaskThreadPool = Executors.newCachedThreadPool();
        }
    }

    /**
     * 该方法用来启动所有的定时任务
     * @throws SchedulerException
     */
    public void scheduleJobs() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        ScheduleFactory.scheduleReportHeartbeat(scheduler); // 心跳任务
        ScheduleFactory.scheduleUpdateStrategy(scheduler);
        ScheduleFactory.scheduleIncrIntegrated(scheduler);
        ScheduleFactory.scheduleFilePut(scheduler);
        ScheduleFactory.scheduleFileRePut(scheduler);
        ScheduleFactory.scheduleFileResultStatus(scheduler);
        ScheduleFactory.scheduleHttpInteraction(scheduler);
        ScheduleFactory.scheduleRunlog(scheduler);
        ScheduleFactory.scheduleMonitorStat(scheduler);
    }

    /**
     * 刷新任务
     * @author konggang
     * @date 2018/3/30 13:45
     */
    public void changeTrigger(String triggerName, String cronExper) throws Exception {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        TriggerKey triggerKey = new TriggerKey(triggerName, Scheduler.DEFAULT_GROUP);
        CronTriggerImpl trigger = (CronTriggerImpl) scheduler.getTrigger(triggerKey);
        trigger.setCronExpression(cronExper);
        scheduler.rescheduleJob(triggerKey, trigger);
    }

    /**
     * 暂停任务
     * @author konggang
     * @date 2018/3/30 13:35
     */
    public void pauseJob(String jobName) throws Exception{
        jobName = jobName.trim();
        JobKey jobKey = new JobKey(jobName, Scheduler.DEFAULT_GROUP);
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复任务
     * @author konggang
     * @date 2018/3/30 13:34
     */
    public  void resumeJob(String jobName) throws SchedulerException {
        jobName = jobName.trim();
        JobKey jobKey = new JobKey(jobName, Scheduler.DEFAULT_GROUP);
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduler.resumeJob(jobKey);
    }

}
