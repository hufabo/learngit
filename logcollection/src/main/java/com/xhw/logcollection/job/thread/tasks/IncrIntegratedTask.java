package com.xhw.logcollection.job.thread.tasks;

import com.xhw.logcollection.job.service.IncrExecuteTaskServ;
import com.xhw.logcollection.job.service.IncrGetSourceFileTaskServ;
import com.xhw.logcollection.job.service.IncrIntegratedServ;
import com.xhw.logcollection.job.thread.ctrl.IncrIntegratedCtrlTask;
import com.xhw.logcollection.util.Constant;
import com.xhw.logcollection.util.SpringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 增量数据采集线程执行任务
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-23
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public class IncrIntegratedTask extends AbstractTask implements Runnable {

    private static Log log = LogFactory.getLog(IncrIntegratedTask.class);

    //任务编号=13
    private final String taskId= Constant.TASKID_INCR_INTEGRATED_TASK;

    private String xtlb;

    public IncrIntegratedTask(String xtlb){
        this.setTaskId(this.taskId); //设置任务编号
        this.xtlb = xtlb; //系统类别
        this.setTaskInfo(String.format("增量采集任务，系统类别:%s", xtlb));
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
        IncrIntegratedServ incrIntegratedServ = SpringUtil.getBean(IncrIntegratedServ.class);
        incrIntegratedServ.parseLog(this.xtlb);
    }

    @Override
    protected void close() {
        IncrIntegratedCtrlTask.removeSubmitRecord(this.xtlb);
    }
}
