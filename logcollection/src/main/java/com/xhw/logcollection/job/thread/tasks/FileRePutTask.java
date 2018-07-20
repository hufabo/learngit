package com.xhw.logcollection.job.thread.tasks;

import com.xhw.logcollection.job.service.FileRePut4IncrServ;
import com.xhw.logcollection.job.service.FileRePut4StockServ;
import com.xhw.logcollection.job.thread.ctrl.FilePutCtrlTask;
import com.xhw.logcollection.util.Constant;
import com.xhw.logcollection.util.SpringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 错误数据重传线程任务
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-23
 * @note 单线程任务
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public class FileRePutTask extends AbstractTask implements Runnable {
    private static Log log = LogFactory.getLog(FileRePutTask.class);

    //任务编号=02
    private final String taskId= Constant.TASKID_FILE_RE_PUT_TASK;

    public FileRePutTask(){
        this.setTaskId(this.taskId); //设置任务编号
        this.setTaskInfo(String.format("文件重传任务"));
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
        log.debug("错误数据重传任务：开始任务");
        //注：存量任务 的优先级高于 增量任务 ，所以先处理 存量错误数据 再处理 增量错误数据
        //存量错误数据重传服务
        FileRePut4StockServ stockServ = SpringUtil.getBean(FileRePut4StockServ.class);
        stockServ.fileRePut();

        //增量错误数据重传服务
        FileRePut4IncrServ incrServ = SpringUtil.getBean(FileRePut4IncrServ.class);
        incrServ.fileRePut();
        log.debug("错误数据重传任务：完成任务");
    }

    @Override
    protected void close() {
        //设置任务运行完成
        TaskStatus.statusStopped(taskId);
    }
}
