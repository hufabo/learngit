package com.xhw.logcollection.job.service.impl;

import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.job.service.StopServ;
import org.springframework.stereotype.Service;

/**
 * 后端任务停止接口实现
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-23
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
@Service
@Deprecated
public class StopServImpl implements StopServ {
    /**
     * 停止所有守护线程
     */
    @Override
    public void stopAll() {
        this.stopAllCmd();
    }

    /**
     * 停止 日志解析文件传输守护线程任务
     */
    @Override
    public void stopFilePutDeamonCmd() {
        ContextBean.setCmdFilePutDeamon(ContextBean.Command.STOP);
    }

    /**
     * 停止 错误数据重传守护线程任务
     */
    @Override
    public void stopFileRePutDeamonCmd() {
        ContextBean.setCmdFileRePutDeamon(ContextBean.Command.STOP);
    }

    /**
     * 停止 日志解析文件反馈处理守护线程任务
     */
    @Override
    public void stopFileResultStatusDeamonCmd() {
        ContextBean.setCmdFileResultStatusDeamon(ContextBean.Command.STOP);
    }

    /**
     * 停止 HTTP传输守护线程任务
     */
    @Override
    public void stopHttpInteractionDeamonCmd() {
        ContextBean.setCmdHttpInteractionDeamon(ContextBean.Command.STOP);
    }

    /**
     * 停止 增量数据采集守护线程拆分任务
     */
    @Override
    public void stopIncrSplitDeamonCmd() {
        ContextBean.setCmdIncrSplitDeamon(ContextBean.Command.STOP);
    }

    /**
     * 停止 心跳状态上报守护线程任务
     */
    @Override
    public void stopReportHeartbeatDeamonCmd() {
        ContextBean.setCmdReportHeartbeatDeamon(ContextBean.Command.STOP);
    }

    /**
     * 停止 存量数据采集守护线程拆分任务
     */
    @Override
    public void stopStockSplitDeamonCmd() {
        ContextBean.setCmdStockSplitDeamon(ContextBean.Command.STOP);
    }

    /**
     * 停止 采集策略更新守护线程任务
     */
    @Override
    public void stopUpdateStrategyDeamonCmd() {
        ContextBean.setCmdUpdateStrategyDeamon(ContextBean.Command.STOP);
    }

    /**
     * 停止 增量源数据文件的采集守护线程任务
     */
    @Override
    public void stopIncrGetSourceFileDeamonCmd() {
        ContextBean.setCmdIncrGetSourceFileDeamon(ContextBean.Command.STOP);
    }

    //停止所有后端守护线程命令
    private void stopAllCmd(){
        //服务状态：1=启用，2=禁用
        ///ContextBean.setCmdFilePutDeamon(ContextBean.Command.STOP);
        ContextBean.setCmdFileRePutDeamon(ContextBean.Command.STOP);
        ContextBean.setCmdFileResultStatusDeamon(ContextBean.Command.STOP);
        ContextBean.setCmdHttpInteractionDeamon(ContextBean.Command.STOP);
        ContextBean.setCmdIncrSplitDeamon(ContextBean.Command.STOP);
        ContextBean.setCmdReportHeartbeatDeamon(ContextBean.Command.STOP);
        ContextBean.setCmdStockSplitDeamon(ContextBean.Command.STOP);
        ContextBean.setCmdUpdateStrategyDeamon(ContextBean.Command.STOP);
        ContextBean.setCmdIncrGetSourceFileDeamon(ContextBean.Command.STOP);
    }
}
