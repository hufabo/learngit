package com.xhw.logcollection.job.thread.tasks;

import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.job.service.ReportHeartbeatServ;
import com.xhw.logcollection.job.thread.ctrl.StockSplitCtrlTask;
import com.xhw.logcollection.util.Constant;
import com.xhw.logcollection.util.SpringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 心跳状态上报线程任务
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-23
 * @note 单线程任务
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public class ReportHeartbeatTask extends AbstractTask implements Runnable {

    private static Log log = LogFactory.getLog(ReportHeartbeatTask.class);

    //任务编号=10
    private final String taskId= Constant.TASKID_REPORT_HEARTBEAT_TASK;

    public ReportHeartbeatTask(){
        this.setTaskId(this.taskId); //设置任务编号
        this.setTaskInfo(String.format("心跳上报任务"));
    }

    @Override
    public void run() {
        //设置任务正在运行
        TaskStatus.statusRunning(taskId);
        this.runTask();
    }

    //错误记录
    @Override
    protected void logError(){

    }
    //业务逻辑
    @Override
    protected void runBusi() throws Exception{
        log.debug("心跳状态上报线程任务：开始心跳上报");
        //调用webservice接口 每10分钟上传 CPU 使用率、内存使用率、磁盘使用率、系统负载等信息
        ReportHeartbeatServ reportHeartbeatServ = SpringUtil.getBean(ReportHeartbeatServ.class);
        reportHeartbeatServ.reportHeartbeat();
        log.debug("心跳状态上报线程任务：完成心跳上报");
    }

    @Override
    protected void close() {
        //设置任务运行完成
        TaskStatus.statusStopped(taskId);
    }
}
