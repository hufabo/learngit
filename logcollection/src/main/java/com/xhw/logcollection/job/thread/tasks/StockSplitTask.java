package com.xhw.logcollection.job.thread.tasks;

import com.xhw.logcollection.job.entity.BusCfgTask;
import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.job.service.StockSplitTaskServ;
import com.xhw.logcollection.job.thread.ctrl.StockSplitCtrlTask;
import com.xhw.logcollection.util.Constant;
import com.xhw.logcollection.util.SpringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * 存量数据采集线程拆分任务
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-23
 * @note 单线程任务
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public class StockSplitTask extends AbstractTask implements Runnable {
    private static Log log = LogFactory.getLog(StockSplitTask.class);

    private BusCfgTask beanTask;

    //任务编号=04
    private final String taskId= Constant.TASKID_STOCK_SPLIT_TASK;

    public StockSplitTask(BusCfgTask beanTask){
        this.setTaskId(this.taskId); //设置任务编号
        this.beanTask = beanTask;

        this.setTaskInfo(String.format("存量执行任务，系统类别:%s，表名：%s",
                beanTask.getJgxtlb(), beanTask.getBm()));
    }

    private volatile static boolean isRunning = false;

    public static boolean getIsRunning() {
        return isRunning;
    }

    public static void setIsRunning(boolean flag) {
        isRunning = flag;
    }

    @Override
    public void run() {
        //设置任务正在运行
        setIsRunning(true);

        this.runTask();
    }

    //错误记录
    @Override
    protected void logError(){

    }
    //业务逻辑
    @Override
    protected void runBusi() throws Exception{
        System.out.println("==》存量数据采集线程拆分任务");
        log.debug("存量数据采集线程拆分任务：开始任务");
        //1、拆分采集任务
        StockSplitTaskServ serv = SpringUtil.getBean(StockSplitTaskServ.class);
        serv.splitTask(this.beanTask);

        log.debug("存量数据采集线程拆分任务：任务完成");
    }

    @Override
    protected void close() {
        //设置任务运行完成
        setIsRunning(false);
        //移除提交记录
        StockSplitCtrlTask.removeSubmitRecord(beanTask);
    }

}
