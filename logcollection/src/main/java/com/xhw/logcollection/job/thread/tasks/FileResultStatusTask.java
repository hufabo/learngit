package com.xhw.logcollection.job.thread.tasks;

import com.xhw.logcollection.job.service.FileResultStatus4IncrServ;
import com.xhw.logcollection.job.service.FileResultStatus4StockServ;
import com.xhw.logcollection.job.thread.ctrl.StockSplitCtrlTask;
import com.xhw.logcollection.util.Constant;
import com.xhw.logcollection.util.SpringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 日志解析文件反馈处理线程任务
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-23
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public class FileResultStatusTask extends AbstractTask implements Runnable {

    private static Log log = LogFactory.getLog(FileResultStatusTask.class);

    //任务编号=08
    private final String taskId= Constant.TASKID_FILE_RESULT_STATUS_TASK;

    public FileResultStatusTask(){
        this.setTaskId(this.taskId); //设置任务编号
        this.setTaskInfo(String.format("文件状态反馈任务"));
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
        log.debug("日志解析文件反馈处理任务：开始同步状态");
        //存量日志文件解析反馈处理
        FileResultStatus4StockServ stockServ = SpringUtil.getBean(FileResultStatus4StockServ.class);
        stockServ.syncFileStatus();

        //增量日志文件解析反馈处理
        FileResultStatus4IncrServ serv = SpringUtil.getBean(FileResultStatus4IncrServ.class);
        serv.syncFileStatus();

        log.debug("日志解析文件反馈处理任务：完成同步状态");

        //1、获取待反馈的记录
        //2、调用webservice接口获取处理结果
        //3、更新状态
    }

    @Override
    protected void close() {
        //设置任务运行完成
        TaskStatus.statusStopped(taskId);
    }

}
