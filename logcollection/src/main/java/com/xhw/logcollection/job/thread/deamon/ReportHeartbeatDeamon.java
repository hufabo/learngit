package com.xhw.logcollection.job.thread.deamon;

import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.job.thread.tasks.ReportHeartbeatTask;
import com.xhw.logcollection.util.Constant;

import java.util.concurrent.TimeUnit;

/**
 * 心跳状态上报守护线程任务
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-09
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
@Deprecated
public class ReportHeartbeatDeamon implements Runnable {
    //任务编号=10
    private final String taskId= Constant.TASKID_REPORT_HEARTBEAT_TASK;

    @Override
    public void run() {
        System.out.println("--->>>心跳状态上报守护线程任务 守护线程-------");

        //死循环处理，可接受外面的退出命令
        while (true) {
            try{
                //退出检查
                if(ContextBean.getCmdReportHeartbeatDeamon()
                        ==  ContextBean.Command.STOP) {
                    //设置为未运行状态
                    ContextBean.setIsRunningReportHeartbeatDeamon(false);
                    this.exit();
                    //退出操作
                    break;
                }
                this.runJob();

                //睡眠
                TimeUnit.MILLISECONDS.sleep(ContextBean.configuration.getIntervalTimeReportHeartbeatDeamon());
            }catch (Exception e) {
                //记录错误信息
                this.errorReport(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    //运行任务
    private void runJob(){
        System.out.println("--->>>>心跳状态上报守护线程任务-------");
//        if(!ReportHeartbeatTask.getIsRunning()) {
//            ContextBean.commonTaskThreadPool.submit(new ReportHeartbeatTask());
//        }
    }
    private void exit(){
        System.out.println("---exit：正在退出“心跳状态上报守护线程任务”");
    }
    private void errorReport(String msg){
        //记录错误信息 TODO ...
        System.out.println("--- 任务-错误-------"+msg);
    }
}
