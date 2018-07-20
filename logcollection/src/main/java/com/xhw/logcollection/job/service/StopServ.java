package com.xhw.logcollection.job.service;

/**
 * 后端任务停止接口
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-09
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public interface StopServ {

    /**
     * 停止所有守护线程
     */
    public void stopAll();
    
    /**
     * 停止 日志解析文件传输守护线程任务
     */
    public void stopFilePutDeamonCmd();

    /**
     * 停止 错误数据重传守护线程任务
     */
    public void stopFileRePutDeamonCmd();

    /**
     * 停止 日志解析文件反馈处理守护线程任务
     */
    public void stopFileResultStatusDeamonCmd();

    /**
     * 停止 HTTP传输守护线程任务
     */
    public void stopHttpInteractionDeamonCmd();

    /**
     * 停止 增量数据采集守护线程拆分任务
     */
    public void stopIncrSplitDeamonCmd();

    /**
     * 停止 增量源数据文件的采集守护线程任务
     */
    public void stopIncrGetSourceFileDeamonCmd();

    /**
     * 停止 心跳状态上报守护线程任务
     */
    public void stopReportHeartbeatDeamonCmd();

    /**
     * 停止 存量数据采集守护线程拆分任务
     */
    public void stopStockSplitDeamonCmd();

    /**
     * 停止 采集策略更新守护线程任务
     */
    public void stopUpdateStrategyDeamonCmd();
}
