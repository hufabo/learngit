package com.xhw.logcollection.job.thread.deamon;

import com.xhw.logcollection.job.entity.BusCfgGlobalBean;
import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.job.thread.ctrl.StockExecuteCtrlTask;
import com.xhw.logcollection.util.Constant;
import com.xhw.logcollection.util.DateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.TimeUnit;

/**
 * 存量数据采集守护线程执行任务
 * 描述：执行拆分后的存量采集子任务
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-09
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
@Deprecated
public class StockExecuteDeamon implements Runnable {
    private static Log log = LogFactory.getLog(StockExecuteDeamon.class);

    //任务编号=05
    private final String taskId= Constant.TASKID_STOCK_EXECUTE_TASK;

    @Override
    public void run() {
        System.out.println("--->>>存量数据采集守护线程执行任务 守护线程-------");

        //死循环处理，可接受外面的退出命令
        while (true) {
            try{
                //退出检查
                if(ContextBean.getCmdStockExecuteDeamon()
                        ==  ContextBean.Command.STOP) {
                    //设置为未运行状态
                    ContextBean.setIsRunningStockExecuteDeamon(false);
                    //退出操作
                    break;
                }
                this.runJob();

                //睡眠
                TimeUnit.MILLISECONDS.sleep(ContextBean.configuration.getIntervalTimeStockExecuteDeamon());
            }catch (Exception e) {
                //记录错误信息
                this.errorReport(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    //运行任务
    private void runJob(){
        //存量采集时间段判断
        int currHours = DateUtil.dateNow().getHours();
        BusCfgGlobalBean busCfgGlobalBean = ContextBean.getBusCfgGlobalBean();
        if(currHours>=busCfgGlobalBean.getClrwqdsj()
                && currHours<=busCfgGlobalBean.getClrwjssj()) {
            log.debug("存量采集任务：开始采集存量数据");
//            if (!StockExecuteCtrlTask.getIsRunning()) {
//                ContextBean.commonTaskThreadPool.submit(new StockExecuteCtrlTask());
//            }
        }else {
            log.debug("存量采集任务：未到采集存量数据的时间");
        }
    }

    private void errorReport(String msg){
        //记录错误信息 TODO ...
        System.out.println("--- 任务-错误-------" + msg);
    }
}
