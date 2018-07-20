package com.xhw.logcollection.job.thread.deamon;

import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.job.thread.tasks.FileResultStatusTask;
import com.xhw.logcollection.util.Constant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.TimeUnit;

/**
 * 日志解析文件反馈处理守护线程任务
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-09
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
@Deprecated
public class FileResultStatusDeamon implements Runnable {
    private static Log log = LogFactory.getLog(FileResultStatusDeamon.class);

    //任务编号=08
    private final String taskId= Constant.TASKID_FILE_RESULT_STATUS_TASK;

    @Override
    public void run() {
        log.info("守护线程任务：开始运行“日志解析文件反馈处理守护线程任务”");

        //死循环处理，可接受外面的退出命令
        while (true) {
            try{
                //退出检查
                if(ContextBean.getCmdFileResultStatusDeamon()
                        ==  ContextBean.Command.STOP) {
                    //设置为未运行状态
                    ContextBean.setIsRunningFileResultStatusDeamon(false);
                    this.exit();
                    //退出操作
                    break;
                }
                this.runJob();

                //睡眠
                TimeUnit.MILLISECONDS.sleep(ContextBean.configuration.getIntervalTimeFileResultStatusDeamon());
            }catch (Exception e) {
                //记录错误信息
                this.errorReport(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    //运行任务
    private void runJob(){
        log.debug("守护线程任务：日志解析文件反馈处理守护线程任务");
//        if(!FileResultStatusTask.getIsRunning()) {
//            ContextBean.commonTaskThreadPool.submit(new FileResultStatusTask());
//        }
    }
    private void exit(){
        System.out.println("---exit：正在退出“日志解析文件反馈处理守护线程任务”");
    }
    private void errorReport(String msg){
        //记录错误信息 TODO ...
        System.out.println("--- 任务-错误-------"+msg);
    }
}
