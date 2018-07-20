package com.xhw.logcollection.job.thread.tasks;

import com.xhw.logcollection.job.service.MonitorStatServ;
import com.xhw.logcollection.job.thread.ctrl.MonitorStatCtrlTask;
import com.xhw.logcollection.util.Constant;
import com.xhw.logcollection.util.SpringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 软件运行状态统计任务
 */
public class MonitorStatTask extends AbstractTask implements Runnable {

    private static Log log = LogFactory.getLog(MonitorStatTask.class);

    //任务编号=11
    private final String taskId= Constant.TASKID_MONITOR_STAT_TASK;
    private String xtlb;

    private volatile static boolean isRunning = false;

    public MonitorStatTask(String xtlb){
        this.setTaskId(this.taskId); //设置任务编号
        this.xtlb = xtlb;

        this.setTaskInfo(String.format("任务运行状态监控任务，系统类别:%s", xtlb));
    }

    public static boolean getIsRunning() {
        return isRunning;
    }

    public static void setIsRunning(boolean flag) {
        isRunning = flag;
    }

    @Override
    public void run() {
        //设置任务正在运行
        setIsRunning(true);
        this.runTask();
    }

    //错误记录
    @Override
    protected void logError(){

    }

    //业务逻辑
    @Override
    protected void runBusi() throws Exception{
        MonitorStatServ serv = SpringUtil.getBean(MonitorStatServ.class);
        serv.queryAndSaveMonitorStat(xtlb);
    }

    @Override
    protected void close() {
        //设置任务运行完成
        setIsRunning(false);
        MonitorStatCtrlTask.removeSubmitRecord(xtlb);
    }
}
