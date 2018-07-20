package com.xhw.logcollection.job.thread.tasks;

import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.job.service.StockExecuteTaskServ;
import com.xhw.logcollection.job.thread.ctrl.StockExecuteCtrlTask;
import com.xhw.logcollection.monitor.entity.BusStockBreakPointInfo;
import com.xhw.logcollection.util.Constant;
import com.xhw.logcollection.util.SpringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.TimeUnit;

/**
 * 存量数据采集线程执行任务
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-23
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public class StockExecuteTask extends AbstractTask implements Runnable {
    private static Log log = LogFactory.getLog(StockExecuteTask.class);
    private BusStockBreakPointInfo beanTask;

    //任务编号=05
    private final String taskId= Constant.TASKID_STOCK_EXECUTE_TASK;

    public StockExecuteTask(BusStockBreakPointInfo beanTask) {
        this.setTaskId(this.taskId); //设置任务编号
        this.beanTask = beanTask;

        this.setTaskInfo(String.format("存量执行任务，系统类别:%s，表名：%s, 数据块编号：%s",
                beanTask.getJgxtlb(), beanTask.getBm(), beanTask.getSjkbh()));
    }

    @Override
    public void run() {
        this.runTask();
    }

    //错误记录
    @Override
    protected void logError(){

    }
    //业务逻辑
    @Override
    protected void runBusi() throws Exception{
        System.out.println("==》存量数据采集线程执行任务");
        log.debug("存量数据采集断点线程任务：开始任务");
        //1、调用存量采集任务接口
        StockExecuteTaskServ serv = SpringUtil.getBean(StockExecuteTaskServ.class);
        serv.executeTask(this.beanTask);
        log.debug("存量数据采集断点线程任务：完成任务");
    }

    @Override
    protected void close() {
        //移除提交记录
        StockExecuteCtrlTask.removeSubmitRecord(this.beanTask);
    }
}
