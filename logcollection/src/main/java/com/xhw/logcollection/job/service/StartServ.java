package com.xhw.logcollection.job.service;


/**
 * 后端任务启动接口
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-09
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
@Deprecated
public interface StartServ {
    public void start();

    /**
     * 启动所有守护线程
     */
    public void startAll();

    /**
     * 启动 日志解析文件传输守护线程任务
     */
    public void startFilePutDeamonCmd();

    /**
     * 启动 错误数据重传守护线程任务
     */
    public void startFileRePutDeamonCmd();

    /**
     * 启动 日志解析文件反馈处理守护线程任务
     */
    public void startFileResultStatusDeamonCmd();

    /**
     * 启动 HTTP传输守护线程任务
     */
    public void startHttpInteractionDeamonCmd();

    /**
     * 启动 心跳状态上报守护线程任务
     */
    public void startReportHeartbeatDeamonCmd();

    /**
     * 启动 存量数据采集守护线程拆分任务
     */
    public void startStockSplitDeamonCmd();

    /**
     * 启动 增量源数据文件的采集守护线程任务
     */
    public void startIncrGetSourceFileDeamonCmd();

    /**
     * 启动 采集策略更新守护线程任务
     */
    public void startUpdateStrategyDeamonCmd();
}
