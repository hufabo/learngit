package com.xhw.logcollection.job.thread.tasks;

import com.xhw.logcollection.job.service.RunlogServ;
import com.xhw.logcollection.job.thread.ctrl.RunlogCtrlTask;
import com.xhw.logcollection.util.Constant;
import com.xhw.logcollection.util.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 软件运行状态统计任务
 */
public class RunlogTask extends AbstractTask implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(RunlogTask.class);

    //任务编号=11
    private final String taskId= Constant.TASKID_RUNLOG_TASK;
    private String xtlb;

    public RunlogTask(String xtlb){
        this.setTaskId(this.taskId); //设置任务编号
        this.xtlb = xtlb;

        this.setTaskInfo(String.format("软件运行状态统计任务，系统类别:%s", xtlb));
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
        RunlogServ serv = SpringUtil.getBean(RunlogServ.class);
        serv.snapshot(xtlb);
    }

    @Override
    protected void close() {
        RunlogCtrlTask.removeSubmitRecord(xtlb);
    }
}
