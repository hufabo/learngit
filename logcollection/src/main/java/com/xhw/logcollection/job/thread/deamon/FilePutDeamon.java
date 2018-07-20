package com.xhw.logcollection.job.thread.deamon;

import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.job.service.StopServ;
import com.xhw.logcollection.job.thread.ctrl.FilePutCtrlTask;
import com.xhw.logcollection.util.Constant;
import com.xhw.logcollection.util.SpringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * 日志解析文件传输守护线程任务
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-09
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
@Deprecated
public class FilePutDeamon implements Runnable {
    private static Log log = LogFactory.getLog(FilePutDeamon.class);

    //任务编号=01
    private final String taskId= Constant.TASKID_FILE_PUT_TASK;

    //心跳时间
    private static long heartbeatTime = 0L;

    public FilePutDeamon(){

    }

    @Override
    public void run() {
        System.out.println("\r\n\r\n--->>>日志解析文件传输守护线程任务 守护线程-------");

        //死循环处理，可接受外面的退出命令
        while (true) {
            try{
                //退出检查
                if(ContextBean.getCmdFilePutDeamon()
                        ==  ContextBean.Command.STOP) {
                    //设置为未运行状态
                    ContextBean.setIsRunningFilePutDeamon(false);
                    this.exit();
                    //退出操作
                    break;
                }
                //更新心跳时间
                heartbeatTime = System.currentTimeMillis();

                this.runJob();

                //睡眠
                TimeUnit.MILLISECONDS.sleep(ContextBean.configuration.getIntervalTimeFilePutDeamon());
            }catch (Exception e) {
                //记录错误信息
                this.errorReport(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    //运行任务
    private void runJob(){
        log.debug("守护线程任务：日志解析文件传输线程任务");
//        if(!FilePutCtrlTask.getIsRunning()) {
//            ContextBean.commonTaskThreadPool.submit(new FilePutCtrlTask());
//        }
    }

    private void exit(){
        System.out.println("---exit：正在退出“日志解析文件传输守护线程任务”");
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
