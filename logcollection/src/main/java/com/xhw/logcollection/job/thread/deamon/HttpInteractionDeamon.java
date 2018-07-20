package com.xhw.logcollection.job.thread.deamon;

import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.job.thread.tasks.HttpInteractionTask;
import com.xhw.logcollection.util.Constant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.TimeUnit;

/**
 * HTTP传输守护线程任务
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-09
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
@Deprecated
public class HttpInteractionDeamon implements Runnable {
    private static Log log = LogFactory.getLog(HttpInteractionDeamon.class);

    //任务编号=09
    private final String taskId= Constant.TASKID_HTTP_INTERACTION_TASK;;

    @Override
    public void run() {
        System.out.println("--->>>HTTP传输守护线程任务 守护线程-------");

        //死循环处理，可接受外面的退出命令
        while (true) {
            try{
                //退出检查
                if(ContextBean.getCmdHttpInteractionDeamon()
                        ==  ContextBean.Command.STOP) {
                    //设置为未运行状态
                    ContextBean.setIsRunningHttpInteractionDeamon(false);
                    this.exit();
                    //退出操作
                    break;
                }
                this.runJob();

                //睡眠
                TimeUnit.MILLISECONDS.sleep(ContextBean.configuration.getIntervalTimeHttpInteractionDeamon());
            }catch (Exception e) {
                //记录错误信息
                this.errorReport(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    //运行任务
    private void runJob(){
        System.out.println("--->>>>HTTP传输守护线程任务-------");
        log.debug("守护线程任务：HTTP传输线程任务");
//        if(!HttpInteractionTask.getIsRunning()) {
//            ContextBean.commonTaskThreadPool.submit(new HttpInteractionTask());
//        }
    }
    private void exit(){
        System.out.println("---exit：正在退出“HTTP传输守护线程任务”");
    }
    private void errorReport(String msg){
        //记录错误信息 TODO ...
        System.out.println("--- 任务-错误-------"+msg);
    }
}
