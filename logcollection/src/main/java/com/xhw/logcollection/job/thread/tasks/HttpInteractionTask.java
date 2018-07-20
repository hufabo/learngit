package com.xhw.logcollection.job.thread.tasks;

import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.job.service.HttpInteractionServ;
import com.xhw.logcollection.job.thread.ctrl.StockSplitCtrlTask;
import com.xhw.logcollection.util.Constant;
import com.xhw.logcollection.util.SpringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * HTTP传输守程任务
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-23
 * @note 单线程任务
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public class HttpInteractionTask extends AbstractTask implements Runnable {
    private static Log log = LogFactory.getLog(HttpInteractionTask.class);

    //任务编号=09
    private final String taskId= Constant.TASKID_HTTP_INTERACTION_TASK;

    public HttpInteractionTask(){
        this.setTaskId(this.taskId); //设置任务编号
        this.setTaskInfo(String.format("http信息上传任务"));
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
        System.out.println("==》HTTP传输线程任务");
        log.debug("HTTP传输线程任务：开始任务");
        HttpInteractionServ serv = SpringUtil.getBean(HttpInteractionServ.class);
        serv.httpInteraction();
    }

    @Override
    protected void close() {
        //设置任务运行完成
        TaskStatus.statusStopped(taskId);
    }
}
