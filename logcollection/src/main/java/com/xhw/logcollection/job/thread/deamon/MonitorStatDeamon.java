package com.xhw.logcollection.job.thread.deamon;

import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.job.thread.ctrl.MonitorStatCtrlTask;
import com.xhw.logcollection.job.thread.ctrl.RunlogCtrlTask;
import com.xhw.logcollection.util.Constant;

import java.util.concurrent.TimeUnit;

/**
 * 软件运行状态守护进程
 */
@Deprecated
public class MonitorStatDeamon implements Runnable {

    //任务编号=11
    private final String taskId = Constant.TASKID_MONITOR_STAT_TASK;

    @Override
    public void run() {
        System.out.println("--->>>采集情况统计守护线程任务 -------");

        //死循环处理，可接受外面的退出命令
        while (true) {
            try{
                //退出检查
                if(ContextBean.getCmdMonitorStatDeamon()
                        ==  ContextBean.Command.STOP) {
                    //设置为未运行状态
                    ContextBean.setIsRunningMonitorStatDeamon(false);
                    this.exit();
                    //退出操作
                    break;
                }

                this.runJob();

            }catch (Exception e) {
                //记录错误信息
                this.errorReport(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    //运行任务
    private void runJob(){
//        if(!MonitorStatCtrlTask.getIsRunning()) {
//            ContextBean.commonTaskThreadPool.submit(new MonitorStatCtrlTask());
//        }
    }
    private void exit(){

    }
    private void errorReport(String msg){
        //记录错误信息 TODO ...
        System.out.println("--- 任务-错误-------" + msg);
    }
}
