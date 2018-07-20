package com.xhw.logcollection.job.service.impl;

import com.xhw.logcollection.job.entity.ConfigurationBean;
import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.job.service.StartServ;
import com.xhw.logcollection.job.thread.ctrl.*;
import com.xhw.logcollection.job.thread.deamon.*;
import com.xhw.logcollection.util.LoadGlobalPropertiesUtil;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;

/**
 * 后端任务启动接口实现
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-09
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
@Deprecated
@Service
public class StartServImpl implements StartServ {
    @Override
    public void start() {
        System.out.println(">后端任务启动接口实现---");
        this.init();
        this.startAll();
    }

    /**
     * 启动所有守护线程
     */
    @Override
    public void startAll(){
        this.startAllCmd();
        this.startAllDeamon();
    }

    /**
     * 启动 日志解析文件传输守护线程任务
     */
    @Override
    public void startFilePutDeamonCmd(){
        ContextBean.setCmdFilePutDeamon(ContextBean.Command.START);
        this.startFilePutDeamon();
    }

    /**
     * 启动 错误数据重传守护线程任务
     */
    @Override
    public void startFileRePutDeamonCmd(){
        ContextBean.setCmdFileRePutDeamon(ContextBean.Command.START);
        this.startFileRePutDeamon();
    }

    /**
     * 启动 日志解析文件反馈处理守护线程任务
     */
    @Override
    public void startFileResultStatusDeamonCmd(){
        ContextBean.setCmdFileResultStatusDeamon(ContextBean.Command.START);
        this.startFileResultStatusDeamon();
    }

    /**
     * 启动 HTTP传输守护线程任务
     */
    @Override
    public void startHttpInteractionDeamonCmd(){
        ContextBean.setCmdHttpInteractionDeamon(ContextBean.Command.START);
        this.startHttpInteractionDeamon();
    }


    /**
     * 启动 心跳状态上报守护线程任务
     */
    @Override
    public void startReportHeartbeatDeamonCmd(){
        ContextBean.setCmdReportHeartbeatDeamon(ContextBean.Command.START);
        this.startReportHeartbeatDeamon();
    }

    /**
     * 启动 存量数据采集守护线程拆分任务
     */
    @Override
    public void startStockSplitDeamonCmd(){
        ContextBean.setCmdStockSplitDeamon(ContextBean.Command.START);
        this.startStockSplitDeamon();
    }

    /**
     * 启动 增量源数据文件的采集守护线程任务
     */
    @Override
    public void startIncrGetSourceFileDeamonCmd(){
        ContextBean.setCmdIncrGetSourceFileDeamon(ContextBean.Command.START);
        this.startIncrGetSourceFileDeamon();
    }

    /**
     * 启动 采集策略更新守护线程任务
     */
    @Override
    public void startUpdateStrategyDeamonCmd(){
        ContextBean.setCmdUpdateStrategyDeamon(ContextBean.Command.START);
        this.startUpdateStrategyDeamon();
    }


    private void init(){
        //初始化后端任务启动参数
        if(ContextBean.configuration == null){
            ConfigurationBean cfg = new ConfigurationBean();
            // 从全局配置文件读取 线程间隔时间 配置
            cfg.setIntervalTimeFilePutDeamon(Integer.valueOf(LoadGlobalPropertiesUtil.getProperty("task.thread.interval.fileput")) * 1000);
            cfg.setIntervalTimeFileRePutDeamon(Integer.valueOf(LoadGlobalPropertiesUtil.getProperty("task.thread.interval.filereput")) * 1000);
            cfg.setIntervalTimeFileResultStatusDeamon(Integer.valueOf(LoadGlobalPropertiesUtil.getProperty("task.thread.interval.fileresultstatus")) * 1000);
            cfg.setIntervalTimeHttpInteractionDeamon(Integer.valueOf(LoadGlobalPropertiesUtil.getProperty("task.thread.interval.http")) * 1000);
            cfg.setIntervalTimeIncrExecuteDeamon(Integer.valueOf(LoadGlobalPropertiesUtil.getProperty("task.thread.interval.incr.execute")) * 1000);
            cfg.setIntervalTimeIncrSplitDeamon(Integer.valueOf(LoadGlobalPropertiesUtil.getProperty("task.thread.interval.incr.split")) * 1000);
            cfg.setIntervalTimeIncrGetSourceFileDeamon(Integer.valueOf(LoadGlobalPropertiesUtil.getProperty("task.thread.interval.incr.getfile")) * 1000);
            cfg.setIntervalTimeReportHeartbeatDeamon(Integer.valueOf(LoadGlobalPropertiesUtil.getProperty("task.thread.interval.heartbeat")) * 1000);
            cfg.setIntervalTimeStockExecuteDeamon(Integer.valueOf(LoadGlobalPropertiesUtil.getProperty("task.thread.interval.stock.execute")) * 1000);
            cfg.setIntervalTimeStockSplitDeamon(Integer.valueOf(LoadGlobalPropertiesUtil.getProperty("task.thread.interval.stock.split")) * 1000);
            cfg.setIntervalTimeUpdateStrategyDeamon(Integer.valueOf(LoadGlobalPropertiesUtil.getProperty("task.thread.interval.updatestrategy")) * 1000);
            cfg.setIntervalTimeRunlogDeamon(Integer.valueOf(LoadGlobalPropertiesUtil.getProperty("task.thread.interval.runlog")) * 1000);
            cfg.setTimePointMonitorStatDeamon(Integer.valueOf(LoadGlobalPropertiesUtil.getProperty("task.thread.timepoint.monitor.stat")));
            ContextBean.configuration = cfg ;
        }

        //初始化公共线程池
        if(ContextBean.commonTaskThreadPool == null){
            ContextBean.commonTaskThreadPool = Executors.newCachedThreadPool();
        }
    }


    //启动所有后端守护线程命令
    private void startAllCmd(){
        //服务状态：1=启用，2=禁用

//        ContextBean.setCmdHttpInteractionDeamon(ContextBean.Command.START);
//        ContextBean.setCmdFileResultStatusDeamon(ContextBean.Command.START);

        ////ContextBean.setCmdIncrSplitDeamon(ContextBean.Command.START);
//        ContextBean.setCmdIncrGetSourceFileDeamon(ContextBean.Command.START);
//        ContextBean.setCmdIncrExecuteDeamon(ContextBean.Command.START);

//        ContextBean.setCmdReportHeartbeatDeamon(ContextBean.Command.START);
//        ContextBean.setCmdUpdateStrategyDeamon(ContextBean.Command.START);
//        ContextBean.setCmdStockSplitDeamon(ContextBean.Command.START);
//        ContextBean.setCmdStockExecuteDeamon(ContextBean.Command.START);
//        ContextBean.setCmdFilePutDeamon(ContextBean.Command.START);
//        ContextBean.setCmdFileRePutDeamon(ContextBean.Command.START);

//        ContextBean.setCmdRunlogDeamon(ContextBean.Command.START);
//        ContextBean.setCmdMonitorStatDeamon(ContextBean.Command.START);
    }

    //启动所有守护线程
    private void startAllDeamon(){
        //启动后端守护线程
        this.startFilePutDeamon();
        this.startFileRePutDeamon();

        this.startReportHeartbeatDeamon();
        this.startStockSplitDeamon();
        this.startStockExecuteDeamon();
        this.startUpdateStrategyDeamon();

        this.startRunlogDeamon();
        this.startMonitorStatDeamon();

        this.startIncrGetSourceFileDeamon();
        this.startIncrExecuteDeamon();

        this.startFileResultStatusDeamon();
        this.startHttpInteractionDeamon();
    }

    //启动 日志解析文件传输守护线程任务
    private void startFilePutDeamon(){
        if(!ContextBean.getIsRunningFilePutDeamon()
                && ContextBean.getCmdFilePutDeamon() == ContextBean.Command.START){
            ContextBean.setIsRunningFilePutDeamon(true);

            //初始化
            FilePutCtrlTask.init();

            //启动后台守护线程
            new Thread(new FilePutDeamon()).start();
        }
    }

    //启动 错误数据重传守护线程任务
    private void startFileRePutDeamon(){
        if(!ContextBean.getIsRunningFileRePutDeamon()
                && ContextBean.getCmdFileRePutDeamon() == ContextBean.Command.START){
            ContextBean.setIsRunningFileRePutDeamon(true);
            new Thread(new FileRePutDeamon()).start();
        }
    }

    //启动 日志解析文件反馈处理守护线程任务
    private void startFileResultStatusDeamon(){
        if(!ContextBean.getIsRunningFileResultStatusDeamon()
                && ContextBean.getCmdFileResultStatusDeamon() == ContextBean.Command.START){
            ContextBean.setIsRunningFileResultStatusDeamon(true);
            new Thread(new FileResultStatusDeamon()).start();
        }
    }

    //启动 HTTP传输守护线程任务
    private void startHttpInteractionDeamon(){
        if(!ContextBean.getIsRunningHttpInteractionDeamon()
                && ContextBean.getCmdHttpInteractionDeamon() == ContextBean.Command.START){
            ContextBean.setIsRunningHttpInteractionDeamon(true);
            new Thread(new HttpInteractionDeamon()).start();
        }
    }

    //增量数据Oracle归档文件或在线文件的采集守护线程任务
    private void startIncrGetSourceFileDeamon(){
        if(!ContextBean.getIsRunningIncrGetSourceFileDeamon()
                && ContextBean.getCmdIncrGetSourceFileDeamon() == ContextBean.Command.START){
            ContextBean.setIsRunningIncrGetSourceFileDeamon(true);

            //初始化
            IncrGetSourceFileCtrlTask.init();

            //启动后台守护线程
            new Thread(new IncrGetSourceFileDeamon()).start();
        }
    }
    //增量数据Oracle归档文件或在线文件的解析守护线程任务
    private void startIncrExecuteDeamon(){
        if(!ContextBean.getIsRunningIncrExecuteDeamon()
                && ContextBean.getCmdIncrExecuteDeamon() == ContextBean.Command.START){
            ContextBean.setIsRunningIncrExecuteDeamon(true);

            //初始化
            IncrExecuteCtrlTask.init();

            //启动后台守护线程
            new Thread(new IncrExecuteDeamon()).start();
        }
    }

    //启动 心跳状态上报守护线程任务
    private void startReportHeartbeatDeamon(){
        if(!ContextBean.getIsRunningReportHeartbeatDeamon()
                && ContextBean.getCmdReportHeartbeatDeamon() == ContextBean.Command.START){
            ContextBean.setIsRunningReportHeartbeatDeamon(true);
            new Thread(new ReportHeartbeatDeamon()).start();
        }
    }

    //启动 存量数据采集守护线程拆分任务
    private void startStockSplitDeamon(){
        if(!ContextBean.getIsRunningStockSplitDeamon()
                && ContextBean.getCmdStockSplitDeamon() == ContextBean.Command.START){
            ContextBean.setIsRunningStockSplitDeamon(true);

            //初始化
            StockSplitCtrlTask.init();
            StockExecuteCtrlTask.init();

            //启动后台守护线程
            new Thread(new StockSplitDeamon()).start();
        }
    }
    //启动 存量数据采集守护线程采集任务
    private void startStockExecuteDeamon(){
        if(!ContextBean.getIsRunningStockExecuteDeamon()
                && ContextBean.getCmdStockExecuteDeamon() == ContextBean.Command.START){
            ContextBean.setIsRunningStockExecuteDeamon(true);

            //初始化
            StockExecuteCtrlTask.init();

            //启动后台守护线程
            new Thread(new StockExecuteDeamon()).start();
        }
    }

    //启动 采集策略更新守护线程任务
    private void startUpdateStrategyDeamon(){
        if(!ContextBean.getIsRunningUpdateStrategyDeamon()
                && ContextBean.getCmdUpdateStrategyDeamon() == ContextBean.Command.START){
            ContextBean.setIsRunningUpdateStrategyDeamon(true);
            new Thread(new UpdateStrategyDeamon()).start();
        }
    }

    //启动 軟件运行状态任务
    private void startRunlogDeamon(){
        if(!ContextBean.getIsRunningRunlogDeamon()
                && ContextBean.getCmdRunlogDeamon() == ContextBean.Command.START){
            ContextBean.setIsRunningRunlogDeamon(true);
            RunlogCtrlTask.init();
            new Thread(new RunlogDeamon()).start();
        }
    }

    //启动 采集策略更新守护线程任务
    private void startMonitorStatDeamon(){
        if(!ContextBean.getIsRunningMonitorStatDeamon()
                && ContextBean.getCmdMonitorStatDeamon() == ContextBean.Command.START){
            ContextBean.setIsRunningMonitorStatDeamon(true);
            MonitorStatCtrlTask.init();
            new Thread(new MonitorStatDeamon()).start();
        }
    }
}
