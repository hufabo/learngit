package com.xhw.logcollection.job.quartz;

import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.util.Constant;
import org.quartz.*;

/**
 * @Author 孔纲
 * @Date 2018/3/30
 */
public class ScheduleFactory {

    /**
     * 心跳监控任务
     * @param scheduler
     * @throws SchedulerException
     */
    public static void scheduleReportHeartbeat(Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(ScheduleReportHeartbeat.class) .withIdentity(Constant.TASKID_REPORT_HEARTBEAT_TASK, Scheduler.DEFAULT_GROUP).build();
        String patternReportHeartbeatDeamon = ContextBean.configuration.getPatternReportHeartbeatDeamon();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(patternReportHeartbeatDeamon);
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger_" + Constant.TASKID_REPORT_HEARTBEAT_TASK, Scheduler.DEFAULT_GROUP) .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }

    /**
     * 采集情况统计任务
     * @param scheduler
     * @throws SchedulerException
     */
    public static void scheduleMonitorStat(Scheduler scheduler) throws SchedulerException{
        JobDetail jobDetail = JobBuilder.newJob(ScheduleMonitorStat.class) .withIdentity(Constant.TASKID_MONITOR_STAT_TASK, Scheduler.DEFAULT_GROUP).build();
        String patternMonitorStatDeamon = ContextBean.configuration.getPatternMonitorStatDeamon();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(patternMonitorStatDeamon);
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger_" + Constant.TASKID_MONITOR_STAT_TASK, Scheduler.DEFAULT_GROUP) .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }

    /**
     * 系统运行情况任务
     * @param scheduler
     * @throws SchedulerException
     */
    public static void scheduleRunlog(Scheduler scheduler) throws SchedulerException{
        JobDetail jobDetail = JobBuilder.newJob(ScheduleRunlog.class) .withIdentity(Constant.TASKID_RUNLOG_TASK, Scheduler.DEFAULT_GROUP).build();
        String patternRunlogDeamon = ContextBean.configuration.getPatternRunlogDeamon();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(patternRunlogDeamon);
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger_" + Constant.TASKID_RUNLOG_TASK, Scheduler.DEFAULT_GROUP) .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }

    /**
     * 更新采集策略任务
     * @param scheduler
     * @throws SchedulerException
     */
    public static void scheduleUpdateStrategy(Scheduler scheduler) throws SchedulerException{
        JobDetail jobDetail = JobBuilder.newJob(ScheduleUpdateStrategy.class) .withIdentity(Constant.TASKID_UPDATE_STRATEGY_TASK, Scheduler.DEFAULT_GROUP).build();
        String patternUpdateStrategyDeamon = ContextBean.configuration.getPatternUpdateStrategyDeamon();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(patternUpdateStrategyDeamon);
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger_" + Constant.TASKID_UPDATE_STRATEGY_TASK, Scheduler.DEFAULT_GROUP) .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }

    /**
     * 存量拆分任务
     * @param scheduler
     * @throws SchedulerException
     */
    public static void scheduleStockSplit(Scheduler scheduler) throws SchedulerException{
        JobDetail jobDetail = JobBuilder.newJob(ScheduleStockSplit.class) .withIdentity(Constant.TASKID_STOCK_SPLIT_TASK, Scheduler.DEFAULT_GROUP).build();
        String patternStockSplitDeamon = ContextBean.configuration.getPatternStockSplitDeamon();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(patternStockSplitDeamon);
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger_" + Constant.TASKID_STOCK_SPLIT_TASK, Scheduler.DEFAULT_GROUP) .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }

    /**
     * 存量采集执行
     * @param scheduler
     * @throws SchedulerException
     */
    public static void scheduleStockExecute(Scheduler scheduler) throws SchedulerException{
        JobDetail jobDetail = JobBuilder.newJob(ScheduleStockExecute.class) .withIdentity(Constant.TASKID_STOCK_EXECUTE_TASK, Scheduler.DEFAULT_GROUP).build();
        String patternStockExecuteDeamon = ContextBean.configuration.getPatternStockExecuteDeamon();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(patternStockExecuteDeamon);
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger_" + Constant.TASKID_STOCK_EXECUTE_TASK, Scheduler.DEFAULT_GROUP) .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }

    /**
     * 文件重传任务
     * @param scheduler
     * @throws SchedulerException
     */
    public static void scheduleFileRePut(Scheduler scheduler) throws SchedulerException{
        JobDetail jobDetail = JobBuilder.newJob(ScheduleFileRePut.class) .withIdentity(Constant.TASKID_FILE_RE_PUT_TASK, Scheduler.DEFAULT_GROUP).build();
        String patternFileRePutDeamon = ContextBean.configuration.getPatternFileRePutDeamon();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(patternFileRePutDeamon);
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger_" + Constant.TASKID_FILE_RE_PUT_TASK, Scheduler.DEFAULT_GROUP) .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }

    /**
     * 文件上传任务
     * @param scheduler
     * @throws SchedulerException
     */
    public static void scheduleFilePut(Scheduler scheduler) throws SchedulerException{
        JobDetail jobDetail = JobBuilder.newJob(ScheduleFilePut.class) .withIdentity(Constant.TASKID_FILE_PUT_TASK, Scheduler.DEFAULT_GROUP).build();
        String patternFilePutDeamon = ContextBean.configuration.getPatternFilePutDeamon();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(patternFilePutDeamon);
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger_" + Constant.TASKID_FILE_PUT_TASK, Scheduler.DEFAULT_GROUP) .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }

    /**
     * 增量日志文件获取
     * @param scheduler
     * @throws SchedulerException
     */
    public static void scheduleIncrGetSourceFile(Scheduler scheduler) throws SchedulerException{
        JobDetail jobDetail = JobBuilder.newJob(ScheduleIncrGetSourceFile.class) .withIdentity(Constant.TASKID_INCR_GET_SOURCE_FILE_TASK, Scheduler.DEFAULT_GROUP).build();
        String patternIncrGetSourceFileDeamon = ContextBean.configuration.getPatternIncrGetSourceFileDeamon();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(patternIncrGetSourceFileDeamon);
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger_" + Constant.TASKID_INCR_GET_SOURCE_FILE_TASK, Scheduler.DEFAULT_GROUP) .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }

    /**
     * 增量解析任务
     * @param scheduler
     * @throws SchedulerException
     */
    public static void scheduleIncrExecute(Scheduler scheduler) throws SchedulerException{
        JobDetail jobDetail = JobBuilder.newJob(ScheduleIncrExecute.class) .withIdentity(Constant.TASKID_INCR_EXECUTE_TASK, Scheduler.DEFAULT_GROUP).build();
        String patternIncrExecuteDeamon = ContextBean.configuration.getPatternIncrExecuteDeamon();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(patternIncrExecuteDeamon);
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger_" + Constant.TASKID_INCR_EXECUTE_TASK, Scheduler.DEFAULT_GROUP) .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }

    /**
     * 文件状态反馈任务
     * @param scheduler
     * @throws SchedulerException
     */
    public static void scheduleFileResultStatus(Scheduler scheduler) throws SchedulerException{
        JobDetail jobDetail = JobBuilder.newJob(ScheduleFileResultStatus.class) .withIdentity(Constant.TASKID_FILE_RESULT_STATUS_TASK, Scheduler.DEFAULT_GROUP).build();
        String patternFileResultStatusDeamon = ContextBean.configuration.getPatternFileResultStatusDeamon();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(patternFileResultStatusDeamon);
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger_" + Constant.TASKID_FILE_RESULT_STATUS_TASK, Scheduler.DEFAULT_GROUP) .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }

    /**
     * HTTP任务
     * @param scheduler
     * @throws SchedulerException
     */
    public static void scheduleHttpInteraction(Scheduler scheduler) throws SchedulerException{
        JobDetail jobDetail = JobBuilder.newJob(ScheduleHttpInteraction.class) .withIdentity(Constant.TASKID_HTTP_INTERACTION_TASK, Scheduler.DEFAULT_GROUP).build();
        String patternHttpInteractionDeamon = ContextBean.configuration.getPatternHttpInteractionDeamon();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(patternHttpInteractionDeamon);
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger_" + Constant.TASKID_HTTP_INTERACTION_TASK, Scheduler.DEFAULT_GROUP) .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }

    /**
     * HTTP任务
     * @param scheduler
     * @throws SchedulerException
     */
    public static void scheduleIncrIntegrated(Scheduler scheduler) throws SchedulerException{
        JobDetail jobDetail = JobBuilder.newJob(ScheduleIncrIntegrated.class) .withIdentity(Constant.TASKID_INCR_INTEGRATED_TASK, Scheduler.DEFAULT_GROUP).build();
        String patternIncrIntegratedDeamon = ContextBean.configuration.getPatternIncrIntegratedDeamon();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(patternIncrIntegratedDeamon);
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger_" + Constant.TASKID_INCR_INTEGRATED_TASK, Scheduler.DEFAULT_GROUP) .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }

}
