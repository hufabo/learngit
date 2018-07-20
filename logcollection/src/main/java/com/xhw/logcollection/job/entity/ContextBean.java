package com.xhw.logcollection.job.entity;

import com.xhw.logcollection.job.service.BusCfgGlobalServ;
import com.xhw.logcollection.util.SpringUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 后端任务上下文类
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-09
 * @note
 * @update
 * 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public class ContextBean {

    // 日志采集全局参数bean实体类
    private volatile static BusCfgGlobalBean busCfgGlobalBean;

    //后端任务配置参数
    public volatile static ConfigurationBean configuration;

    //公共线程池
    public volatile static ExecutorService commonTaskThreadPool;

    /**
     * 运行命令
     */
    //日志解析文件传输守护线程任务命令
    private volatile static Command cmdFilePutDeamon			= Command.UNKNOWN;
    //错误数据重传守护线程任务
    private volatile static Command cmdFileRePutDeamon			= Command.UNKNOWN;
    //日志解析文件反馈处理守护线程任务
    private volatile static Command cmdFileResultStatusDeamon   = Command.UNKNOWN;
    //HTTP传输守护线程任务
    private volatile static Command cmdHttpInteractionDeamon	= Command.UNKNOWN;
    //增量数据采集守护线程执行任务
    private volatile static Command cmdIncrExecuteDeamon		= Command.UNKNOWN;
    //增量数据采集守护线程拆分任务
    private volatile static Command cmdIncrSplitDeamon			= Command.UNKNOWN;
    //增量数据Oracle归档文件或在线文件的采集守护线程任务
    private volatile static Command cmdIncrGetSourceFileDeamon	= Command.UNKNOWN;
    //心跳状态上报守护线程任务
    private volatile static Command cmdReportHeartbeatDeamon    = Command.UNKNOWN;
    //存量数据采集守护线程执行任务
    private volatile static Command cmdStockExecuteDeamon		= Command.UNKNOWN;
    //存量数据采集守护线程拆分任务
    private volatile static Command cmdStockSplitDeamon			= Command.UNKNOWN;
    //采集策略更新守护线程任务
    private volatile static Command cmdUpdateStrategyDeamon		= Command.UNKNOWN;
    //软件运行状况
    private volatile static Command cmdRunlogDeamon		        = Command.UNKNOWN;
    //采集情况统计
    private volatile static Command cmdMonitorStatDeamon		= Command.UNKNOWN;

    /**
     * 运行状态
     */
    //日志解析文件传输守护线程任务
    private volatile static boolean isRunningFilePutDeamon			= false;
    //错误数据重传守护线程任务
    private volatile static boolean isRunningFileRePutDeamon		= false;
    //日志解析文件反馈处理守护线程任务
    private volatile static boolean isRunningFileResultStatusDeamon = false;
    //HTTP传输守护线程任务
    private volatile static boolean isRunningHttpInteractionDeamon  = false;
    //增量数据采集守护线程执行任务
    private volatile static boolean isRunningIncrExecuteDeamon      = false;
    //增量数据采集守护线程拆分任务
    private volatile static boolean isRunningIncrSplitDeamon		= false;
    //增量数据Oracle归档文件或在线文件的采集守护线程任务
    private volatile static boolean isRunningIncrGetSourceFileDeamon		= false;
    //心跳状态上报守护线程任务
    private volatile static boolean isRunningReportHeartbeatDeamon  = false;
    //存量数据采集守护线程执行任务
    private volatile static boolean isRunningStockExecuteDeamon		= false;
    //存量数据采集守护线程拆分任务
    private volatile static boolean isRunningStockSplitDeamon		= false;
    //采集策略更新守护线程任务
    private volatile static boolean isRunningUpdateStrategyDeamon	= false;
    //软件运行状况
    private volatile static boolean isRunningRunlogDeamon	= false;
    //采集情况统计
    private volatile static boolean isRunningMonitorStatDeamon	= false;


    public static Command getCmdFilePutDeamon() {
        return cmdFilePutDeamon;
    }

    public static void setCmdFilePutDeamon(Command cmdFilePutDeamon) {
        ContextBean.cmdFilePutDeamon = cmdFilePutDeamon;
    }

    public static Command getCmdFileRePutDeamon() {
        return cmdFileRePutDeamon;
    }

    public static void setCmdFileRePutDeamon(Command cmdFileRePutDeamon) {
        ContextBean.cmdFileRePutDeamon = cmdFileRePutDeamon;
    }

    public static Command getCmdFileResultStatusDeamon() {
        return cmdFileResultStatusDeamon;
    }

    public static void setCmdFileResultStatusDeamon(Command cmdFileResultStatusDeamon) {
        ContextBean.cmdFileResultStatusDeamon = cmdFileResultStatusDeamon;
    }

    public static Command getCmdHttpInteractionDeamon() {
        return cmdHttpInteractionDeamon;
    }

    public static void setCmdHttpInteractionDeamon(Command cmdHttpInteractionDeamon) {
        ContextBean.cmdHttpInteractionDeamon = cmdHttpInteractionDeamon;
    }

    public static Command getCmdIncrExecuteDeamon() {
        return cmdIncrExecuteDeamon;
    }

    public static void setCmdIncrExecuteDeamon(Command cmdIncrExecuteDeamon) {
        ContextBean.cmdIncrExecuteDeamon = cmdIncrExecuteDeamon;
    }

    public static Command getCmdIncrSplitDeamon() {
        return cmdIncrSplitDeamon;
    }

    public static void setCmdIncrSplitDeamon(Command cmdIncrSplitDeamon) {
        ContextBean.cmdIncrSplitDeamon = cmdIncrSplitDeamon;
    }

    public static Command getCmdReportHeartbeatDeamon() {
        return cmdReportHeartbeatDeamon;
    }

    public static void setCmdReportHeartbeatDeamon(Command cmdReportHeartbeatDeamon) {
        ContextBean.cmdReportHeartbeatDeamon = cmdReportHeartbeatDeamon;
    }

    public static Command getCmdStockExecuteDeamon() {
        return cmdStockExecuteDeamon;
    }

    public static void setCmdStockExecuteDeamon(Command cmdStockExecuteDeamon) {
        ContextBean.cmdStockExecuteDeamon = cmdStockExecuteDeamon;
    }

    public static Command getCmdStockSplitDeamon() {
        return cmdStockSplitDeamon;
    }

    public static void setCmdStockSplitDeamon(Command cmdStockSplitDeamon) {
        ContextBean.cmdStockSplitDeamon = cmdStockSplitDeamon;
    }

    public static Command getCmdUpdateStrategyDeamon() {
        return cmdUpdateStrategyDeamon;
    }

    public static void setCmdUpdateStrategyDeamon(Command cmdUpdateStrategyDeamon) {
        ContextBean.cmdUpdateStrategyDeamon = cmdUpdateStrategyDeamon;
    }

    public static Command getCmdRunlogDeamon() {
        return cmdRunlogDeamon;
    }

    public static void setCmdRunlogDeamon(Command cmdRunlogDeamon) {
        ContextBean.cmdRunlogDeamon = cmdRunlogDeamon;
    }

    public static Command getCmdMonitorStatDeamon() {
        return cmdMonitorStatDeamon;
    }

    public static void setCmdMonitorStatDeamon(Command cmdMonitorStatDeamon) {
        ContextBean.cmdMonitorStatDeamon = cmdMonitorStatDeamon;
    }

    public static Command getCmdIncrGetSourceFileDeamon() {
        return cmdIncrGetSourceFileDeamon;
    }

    public static void setCmdIncrGetSourceFileDeamon(Command cmd) {
        ContextBean.cmdIncrGetSourceFileDeamon = cmd;
    }

    public static boolean getIsRunningFilePutDeamon() {
        return isRunningFilePutDeamon;
    }

    public static void setIsRunningFilePutDeamon(boolean flag) {
        ContextBean.isRunningFilePutDeamon=flag;
    }

    public static boolean getIsRunningFileRePutDeamon() {
        return isRunningFileRePutDeamon;
    }

    public static void setIsRunningFileRePutDeamon(boolean flag) {
        ContextBean.isRunningFileRePutDeamon=flag;
    }

    public static boolean getIsRunningFileResultStatusDeamon() {
        return isRunningFileResultStatusDeamon;
    }

    public static void setIsRunningFileResultStatusDeamon(boolean flag) {
        ContextBean.isRunningFileResultStatusDeamon=flag;
    }

    public static boolean getIsRunningHttpInteractionDeamon() {
        return isRunningHttpInteractionDeamon;
    }

    public static void setIsRunningHttpInteractionDeamon(boolean flag) {
        ContextBean.isRunningHttpInteractionDeamon=flag;
    }

    public static boolean getIsRunningIncrExecuteDeamon() {
        return isRunningIncrExecuteDeamon;
    }

    public static void setIsRunningIncrExecuteDeamon(boolean flag) {
        ContextBean.isRunningIncrExecuteDeamon=flag;
    }

    public static boolean getIsRunningIncrSplitDeamon() {
        return isRunningIncrSplitDeamon;
    }

    public static void setIsRunningIncrSplitDeamon(boolean flag) {
        ContextBean.isRunningIncrSplitDeamon=flag;
    }

    public static boolean getIsRunningReportHeartbeatDeamon() {
        return isRunningReportHeartbeatDeamon;
    }

    public static void setIsRunningReportHeartbeatDeamon(boolean flag) {
        ContextBean.isRunningReportHeartbeatDeamon=flag;
    }

    public static boolean getIsRunningStockExecuteDeamon() {
        return isRunningStockExecuteDeamon;
    }

    public static void setIsRunningStockExecuteDeamon(boolean flag) {
        ContextBean.isRunningStockExecuteDeamon=flag;
    }

    public static boolean getIsRunningStockSplitDeamon() {
        return isRunningStockSplitDeamon;
    }

    public static void setIsRunningStockSplitDeamon(boolean flag) {
        ContextBean.isRunningStockSplitDeamon=flag;
    }

    public static boolean getIsRunningUpdateStrategyDeamon() {
        return isRunningUpdateStrategyDeamon;
    }

    public static void setIsRunningUpdateStrategyDeamon(boolean flag) {
        ContextBean.isRunningUpdateStrategyDeamon=flag;
    }

    public static boolean getIsRunningIncrGetSourceFileDeamon() {
        return isRunningIncrGetSourceFileDeamon;
    }

    public static void setIsRunningIncrGetSourceFileDeamon(boolean flag) {
        ContextBean.isRunningIncrGetSourceFileDeamon = flag;
    }

    public static boolean getIsRunningRunlogDeamon() {
        return isRunningRunlogDeamon;
    }

    public static void setIsRunningRunlogDeamon(boolean flag) {
        ContextBean.isRunningRunlogDeamon = flag;
    }

    public static boolean getIsRunningMonitorStatDeamon() {
        return isRunningMonitorStatDeamon;
    }

    public static void setIsRunningMonitorStatDeamon(boolean flag) {
        ContextBean.isRunningMonitorStatDeamon = flag;
    }

    /**
     * 日志采集全局参数bean实体类
     *
     * @return
     */
    public static synchronized BusCfgGlobalBean getBusCfgGlobalBean() {
        if(busCfgGlobalBean==null){
            BusCfgGlobalServ serv = SpringUtil.getBean(BusCfgGlobalServ.class);
            try {
                busCfgGlobalBean = serv.getBusCfgGlobalBean();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return busCfgGlobalBean;
    }

    /**
     * 日志采集全局参数bean实体类
     * @param busCfgGlobalBean
     */
    public static synchronized void setBusCfgGlobalBean(BusCfgGlobalBean busCfgGlobalBean) {
        ContextBean.busCfgGlobalBean = busCfgGlobalBean;
    }

    //命令枚举类
    public static enum Command{
        /**
         * 启动操作命令
         */
        START(1),
        /**
         * 停止操作命令
         */
        STOP(2),
        /**
         * 未知命令
         */
        UNKNOWN(99);

        private int value;
        private Command(int value){
            this.value = value;
        }

        /**
         * 数值转换为命令
         *
         * @param value 数值
         * @return
         */
        public static Command getValue(int value){
            if(value==1){
                return Command.START;
            }else if(value==2){
                return Command.STOP;
            }

            return Command.UNKNOWN;
        }
        public static Command getValue(String value){
            if(value==null)
                return Command.UNKNOWN;

            try {
                return getValue(Integer.parseInt(value));
            } catch (Exception e) {
                return Command.UNKNOWN;
            }
        }

        /**
         * 命令转换为值
         *
         * @param cmd
         * @return
         */
        public static int getValue(Command cmd){
            return cmd.value;
        }

        public int getValue(){
            return this.value;
        }
    }

    /**
     * 无锡所webservice接口信息枚举类
     */
    public static enum InterfaceID{

        /**
         * 采集配置策略查询接口
         */
        GETSTRATEGY("80Q52"),

        /**
         * 软件心跳信息写入接口
         */
        HEARTBEAT("60W59"),

        /**
         * 软件运行状态信息写入接口
         */
        RUNSTATUS("80W60"),

        /**
         * 存量数据处理状态写入接口
         */
        STOCKDEALSTATUS("80W52"),

        /**
         * 存量数据块断点写入接口
         */
        STOCKBREAKPOINT("80W53"),

        /**
         * 增量数据断点写入接口
         */
        INCREBREAKPOINT("80W54"),

        /**
         * 存量数据文件生成信息写入接口
         */
        STOCKFILE("80W55"),

        /**
         * 增量数据文件生成信息写入接口
         */
        INCREFILE("80W56"),

        /**
         * DDL数据审计写入接口
         */
        AUDIDDL("80W57"),

        /**
         * 数据文件入库反馈写入接口
         */
        FILERSTSTATUS("80W58");

        private String val;//接口ID值
        private InterfaceID(String val){
            this.val = val;
        }

        @Override
        public String toString() {
            return this.val;
        }
    }
}
