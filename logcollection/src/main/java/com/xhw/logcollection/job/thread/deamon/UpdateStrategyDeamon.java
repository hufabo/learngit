package com.xhw.logcollection.job.thread.deamon;

import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.job.thread.tasks.UpdateStrategyTask;
import com.xhw.logcollection.util.Constant;

import java.util.concurrent.TimeUnit;

/**
 * 采集策略更新守护线程任务
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-09
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
@Deprecated
public class UpdateStrategyDeamon implements Runnable {
    //任务编号=03
    private final String taskId = Constant.TASKID_UPDATE_STRATEGY_TASK;

    @Override
    public void run() {
        System.out.println("--->>>采集策略更新守护线程任务 守护线程-------");

        //死循环处理，可接受外面的退出命令
        while (true) {
            try{
                //退出检查
                if(ContextBean.getCmdUpdateStrategyDeamon()
                        ==  ContextBean.Command.STOP) {
                    //设置为未运行状态
                    ContextBean.setIsRunningUpdateStrategyDeamon(false);
                    this.exit();
                    //退出操作
                    break;
                }
                this.runJob();

                //睡眠
                TimeUnit.MILLISECONDS.sleep(ContextBean.configuration.getIntervalTimeUpdateStrategyDeamon());
            }catch (Exception e) {
                //记录错误信息
                this.errorReport(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    //运行任务
    private void runJob(){
        System.out.println("--->>>>采集策略更新守护线程任务-------");
//        if(!UpdateStrategyTask.getIsRunning()) {
//            ContextBean.commonTaskThreadPool.submit(new UpdateStrategyTask());
//        }
    }
    private void exit(){
        System.out.println("---exit：正在退出“采集策略更新守护线程任务”");
    }
    private void errorReport(String msg){
        //记录错误信息 TODO ...
        System.out.println("--- 任务-错误-------"+msg);
    }
}
