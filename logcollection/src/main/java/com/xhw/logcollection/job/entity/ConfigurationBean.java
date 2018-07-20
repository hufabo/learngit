package com.xhw.logcollection.job.entity;

/**
 * 后端任务配置参数
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-09
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public class ConfigurationBean {
    //日志解析文件传输守护线程任务
    private int intervalTimeFilePutDeamon = 30*1000;
    private String patternFilePutDeamon;
    //错误数据重传守护线程任务
    private int intervalTimeFileRePutDeamon			= 30*1000;
    private String patternFileRePutDeamon;
    //日志解析文件反馈处理守护线程任务
    private int intervalTimeFileResultStatusDeamon    = 30*1000;
    private String patternFileResultStatusDeamon;
    //HTTP传输守护线程任务
    private int intervalTimeHttpInteractionDeamon		= 30*1000;
    private String patternHttpInteractionDeamon;
    //增量数据采集守护线程执行任务
    private int intervalTimeIncrExecuteDeamon			= 30*1000;
    private String patternIncrExecuteDeamon;
    //增量数据采集守护线程拆分任务
    private int intervalTimeIncrSplitDeamon			= 30*1000;
    private String patternIncrSplitDeamon;
    //增量数据Oracle归档文件或在线文件的采集守护线程任务
    private int intervalTimeIncrGetSourceFileDeamon			= 30*1000;
    private String patternIncrGetSourceFileDeamon;

    //心跳状态上报守护线程任务
    private int intervalTimeReportHeartbeatDeamon     = 30*1000;
    private String patternReportHeartbeatDeamon;
    //存量数据采集守护线程执行任务
    private int intervalTimeStockExecuteDeamon		= 30*1000;
    private String patternStockExecuteDeamon;
    //存量数据采集守护线程拆分任务
    private int intervalTimeStockSplitDeamon			= 30*1000;
    private String patternStockSplitDeamon;
    //采集策略更新守护线程任务
    private int intervalTimeUpdateStrategyDeamon		= 30*1000;
    private String patternUpdateStrategyDeamon;
    //系统运行状态统计任务周期
    private int intervalTimeRunlogDeamon			= 24 * 60 * 60 * 1000 ;
    private String patternRunlogDeamon;
    //每日采集情况统计任务（时间点）
    private int timePointMonitorStatDeamon		= 2;
    private String patternMonitorStatDeamon;

    private String patternIncrIntegratedDeamon;

    public int getIntervalTimeFilePutDeamon() {
        return intervalTimeFilePutDeamon;
    }

    public void setIntervalTimeFilePutDeamon(int intervalTimeFilePutDeamon) {
        this.intervalTimeFilePutDeamon = intervalTimeFilePutDeamon;
    }

    public int getIntervalTimeFileRePutDeamon() {
        return intervalTimeFileRePutDeamon;
    }

    public void setIntervalTimeFileRePutDeamon(int intervalTimeFileRePutDeamon) {
        this.intervalTimeFileRePutDeamon = intervalTimeFileRePutDeamon;
    }

    public int getIntervalTimeFileResultStatusDeamon() {
        return intervalTimeFileResultStatusDeamon;
    }

    public void setIntervalTimeFileResultStatusDeamon(int intervalTimeFileResultStatusDeamon) {
        this.intervalTimeFileResultStatusDeamon = intervalTimeFileResultStatusDeamon;
    }

    public int getIntervalTimeHttpInteractionDeamon() {
        return intervalTimeHttpInteractionDeamon;
    }

    public void setIntervalTimeHttpInteractionDeamon(int intervalTimeHttpInteractionDeamon) {
        this.intervalTimeHttpInteractionDeamon = intervalTimeHttpInteractionDeamon;
    }

    public int getIntervalTimeIncrExecuteDeamon() {
        return intervalTimeIncrExecuteDeamon;
    }

    public void setIntervalTimeIncrExecuteDeamon(int intervalTimeIncrExecuteDeamon) {
        this.intervalTimeIncrExecuteDeamon = intervalTimeIncrExecuteDeamon;
    }

    public int getIntervalTimeIncrSplitDeamon() {
        return intervalTimeIncrSplitDeamon;
    }

    public void setIntervalTimeIncrSplitDeamon(int intervalTimeIncrSplitDeamon) {
        this.intervalTimeIncrSplitDeamon = intervalTimeIncrSplitDeamon;
    }

    public int getIntervalTimeReportHeartbeatDeamon() {
        return intervalTimeReportHeartbeatDeamon;
    }

    public void setIntervalTimeReportHeartbeatDeamon(int intervalTimeReportHeartbeatDeamon) {
        this.intervalTimeReportHeartbeatDeamon = intervalTimeReportHeartbeatDeamon;
    }

    public int getIntervalTimeStockExecuteDeamon() {
        return intervalTimeStockExecuteDeamon;
    }

    public void setIntervalTimeStockExecuteDeamon(int intervalTimeStockExecuteDeamon) {
        this.intervalTimeStockExecuteDeamon = intervalTimeStockExecuteDeamon;
    }

    public int getIntervalTimeStockSplitDeamon() {
        return intervalTimeStockSplitDeamon;
    }

    public void setIntervalTimeStockSplitDeamon(int intervalTimeStockSplitDeamon) {
        this.intervalTimeStockSplitDeamon = intervalTimeStockSplitDeamon;
    }

    public int getIntervalTimeUpdateStrategyDeamon() {
        return intervalTimeUpdateStrategyDeamon;
    }

    public void setIntervalTimeUpdateStrategyDeamon(int intervalTimeUpdateStrategyDeamon) {
        this.intervalTimeUpdateStrategyDeamon = intervalTimeUpdateStrategyDeamon;
    }

    public int getIntervalTimeIncrGetSourceFileDeamon() {
        return intervalTimeIncrGetSourceFileDeamon;
    }

    public void setIntervalTimeIncrGetSourceFileDeamon(int value) {
        this.intervalTimeIncrGetSourceFileDeamon = value;
    }

    public int getIntervalTimeRunlogDeamon() {
        return intervalTimeRunlogDeamon;
    }

    public void setIntervalTimeRunlogDeamon(int intervalTimeRunlogDeamon) {
        this.intervalTimeRunlogDeamon = intervalTimeRunlogDeamon;
    }

    public int getTimePointMonitorStatDeamon() {
        return timePointMonitorStatDeamon;
    }

    public void setTimePointMonitorStatDeamon(int timePointMonitorStatDeamon) {
        this.timePointMonitorStatDeamon = timePointMonitorStatDeamon;
    }

    public String getPatternFilePutDeamon() {
        return patternFilePutDeamon;
    }

    public void setPatternFilePutDeamon(String patternFilePutDeamon) {
        this.patternFilePutDeamon = patternFilePutDeamon;
    }

    public String getPatternFileRePutDeamon() {
        return patternFileRePutDeamon;
    }

    public void setPatternFileRePutDeamon(String patternFileRePutDeamon) {
        this.patternFileRePutDeamon = patternFileRePutDeamon;
    }

    public String getPatternFileResultStatusDeamon() {
        return patternFileResultStatusDeamon;
    }

    public void setPatternFileResultStatusDeamon(String patternFileResultStatusDeamon) {
        this.patternFileResultStatusDeamon = patternFileResultStatusDeamon;
    }

    public String getPatternHttpInteractionDeamon() {
        return patternHttpInteractionDeamon;
    }

    public void setPatternHttpInteractionDeamon(String patternHttpInteractionDeamon) {
        this.patternHttpInteractionDeamon = patternHttpInteractionDeamon;
    }

    public String getPatternIncrExecuteDeamon() {
        return patternIncrExecuteDeamon;
    }

    public void setPatternIncrExecuteDeamon(String patternIncrExecuteDeamon) {
        this.patternIncrExecuteDeamon = patternIncrExecuteDeamon;
    }

    public String getPatternIncrSplitDeamon() {
        return patternIncrSplitDeamon;
    }

    public void setPatternIncrSplitDeamon(String patternIncrSplitDeamon) {
        this.patternIncrSplitDeamon = patternIncrSplitDeamon;
    }

    public String getPatternIncrGetSourceFileDeamon() {
        return patternIncrGetSourceFileDeamon;
    }

    public void setPatternIncrGetSourceFileDeamon(String patternIncrGetSourceFileDeamon) {
        this.patternIncrGetSourceFileDeamon = patternIncrGetSourceFileDeamon;
    }

    public String getPatternReportHeartbeatDeamon() {
        return patternReportHeartbeatDeamon;
    }

    public void setPatternReportHeartbeatDeamon(String patternReportHeartbeatDeamon) {
        this.patternReportHeartbeatDeamon = patternReportHeartbeatDeamon;
    }

    public String getPatternStockExecuteDeamon() {
        return patternStockExecuteDeamon;
    }

    public void setPatternStockExecuteDeamon(String patternStockExecuteDeamon) {
        this.patternStockExecuteDeamon = patternStockExecuteDeamon;
    }

    public String getPatternStockSplitDeamon() {
        return patternStockSplitDeamon;
    }

    public void setPatternStockSplitDeamon(String patternStockSplitDeamon) {
        this.patternStockSplitDeamon = patternStockSplitDeamon;
    }

    public String getPatternUpdateStrategyDeamon() {
        return patternUpdateStrategyDeamon;
    }

    public void setPatternUpdateStrategyDeamon(String patternUpdateStrategyDeamon) {
        this.patternUpdateStrategyDeamon = patternUpdateStrategyDeamon;
    }

    public String getPatternRunlogDeamon() {
        return patternRunlogDeamon;
    }

    public void setPatternRunlogDeamon(String patternRunlogDeamon) {
        this.patternRunlogDeamon = patternRunlogDeamon;
    }

    public String getPatternMonitorStatDeamon() {
        return patternMonitorStatDeamon;
    }

    public void setPatternMonitorStatDeamon(String patternMonitorStatDeamon) {
        this.patternMonitorStatDeamon = patternMonitorStatDeamon;
    }

    public String getPatternIncrIntegratedDeamon() {
        return patternIncrIntegratedDeamon;
    }

    public void setPatternIncrIntegratedDeamon(String patternIncrIntegratedDeamon) {
        this.patternIncrIntegratedDeamon = patternIncrIntegratedDeamon;
    }
}
