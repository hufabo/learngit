package com.xhw.logcollection.job.thread.deamon;

import com.xhw.logcollection.job.entity.BusCfgGlobalBean;
import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.job.thread.ctrl.IncrExecuteCtrlTask;
import com.xhw.logcollection.util.Constant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.TimeUnit;

/**
 * 增量数据采集守护线程执行任务
 * 描述：解析Oracle归档日志文件或在线文件并生产增量文件
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-09
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
@Deprecated
public class IncrExecuteDeamon implements Runnable {
    private static Log log = LogFactory.getLog(IncrExecuteDeamon.class);

    //任务编号=07
    private final String taskId= Constant.TASKID_INCR_EXECUTE_TASK;

    @Override
    public void run() {
        System.out.println("--->>>增量数据采集守护线程执行任务 守护线程-------");

        //死循环处理，可接受外面的退出命令
        while (true) {
            try{
                //退出检查
                if(ContextBean.getCmdIncrExecuteDeamon()
                        ==  ContextBean.Command.STOP) {
                    //设置为未运行状态
                    ContextBean.setIsRunningIncrExecuteDeamon(false);
                    //退出操作
                    break;
                }
                this.runJob();

                //睡眠
                BusCfgGlobalBean busCfgGlobalBean = ContextBean.getBusCfgGlobalBean();
                if(busCfgGlobalBean==null) {
                    //使用 默认的采集周期时间
                    TimeUnit.MILLISECONDS.sleep(ContextBean.configuration.getIntervalTimeIncrExecuteDeamon());
                } else {
                    //使用 全局参数中的 增量数据采集周期，单位分钟
                    TimeUnit.MILLISECONDS.sleep(busCfgGlobalBean.getZlcjzq()*60*1000);
                }
            }catch (Exception e) {
                //记录错误信息
                this.errorReport(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    //运行任务
    private void runJob(){
        System.out.println("--->>>>增量数据采集守护线程执行任务-------");
        log.debug("守护线程任务：增量数据采集执行任务");
        if(!IncrExecuteCtrlTask.getIsRunning()) {
            ContextBean.commonTaskThreadPool.submit(new IncrExecuteCtrlTask());
        }
    }

    private void errorReport(String msg){
        //记录错误信息 TODO ...
        System.out.println("--- 任务-错误-------"+msg);
    }
}
