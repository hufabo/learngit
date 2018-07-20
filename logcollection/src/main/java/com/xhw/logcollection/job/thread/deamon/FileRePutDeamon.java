package com.xhw.logcollection.job.thread.deamon;

import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.job.thread.tasks.FileRePutTask;
import com.xhw.logcollection.util.Constant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.TimeUnit;

/**
 * 错误数据重传守护线程任务
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-09
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
@Deprecated
public class FileRePutDeamon implements Runnable {
    private static Log log = LogFactory.getLog(FileRePutDeamon.class);
    //任务编号=02
    private final String taskId= Constant.TASKID_FILE_RE_PUT_TASK;

    //心跳时间
    private static long heartbeatTime = 0L;

    @Override
    public void run() {
        System.out.println("--->>>错误数据重传守护线程任务 守护线程-------");

        //死循环处理，可接受外面的退出命令
        while (true) {
            try{
                //退出检查
                if(ContextBean.getCmdFileRePutDeamon()
                        ==  ContextBean.Command.STOP) {
                    //设置为未运行状态
                    ContextBean.setIsRunningFileRePutDeamon(false);
                    this.exit();
                    //退出操作
                    break;
                }
                //更新心跳时间
                heartbeatTime = System.currentTimeMillis();

                this.runJob();

                //睡眠
                TimeUnit.MILLISECONDS.sleep(ContextBean.configuration.getIntervalTimeFileRePutDeamon());
            }catch (Exception e) {
                //记录错误信息
                this.errorReport(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    //运行任务
    private void runJob(){
        log.debug("守护线程任务：错误数据重传守护线程任务");
//        if(!FileRePutTask.getIsRunning()) {
//            ContextBean.commonTaskThreadPool.submit(new FileRePutTask());
//        }
    }
    private void exit(){
        System.out.println("---exit：正在退出“错误数据重传守护线程任务”");
    }
    private void errorReport(String msg){
        //记录错误信息 TODO ...
        System.out.println("--- 任务-错误-------"+msg);
    }

    /**
     * 获取最近的心跳时间
     * @return
     */
    public static long getHeartbeatTime() {
        return heartbeatTime;
    }
}
