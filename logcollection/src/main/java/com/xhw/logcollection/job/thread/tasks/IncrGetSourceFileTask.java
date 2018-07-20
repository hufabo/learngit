package com.xhw.logcollection.job.thread.tasks;

import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.job.service.IncrGetSourceFileTaskServ;
import com.xhw.logcollection.job.thread.ctrl.IncrGetSourceFileCtrlTask;
import com.xhw.logcollection.util.Constant;
import com.xhw.logcollection.util.SpringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * （增量）数据库日志采集线程任务
 * 备注：采集Oracle数据的归档日志或在线日志文件
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-23
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
@Deprecated
public class IncrGetSourceFileTask extends AbstractTask implements Runnable {
    private static Log log = LogFactory.getLog(IncrGetSourceFileTask.class);

    //任务编号=06
    private final String taskId= Constant.TASKID_INCR_GET_SOURCE_FILE_TASK;
    private String dsTypeName;

    public IncrGetSourceFileTask(String dsTypeName){
        this.setTaskId(this.taskId); //设置任务编号
        this.dsTypeName = dsTypeName; //数据源类别名称
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
        log.debug("数据库日志增量采集线程任务：开始采集");
        IncrGetSourceFileTaskServ serv = SpringUtil.getBean(IncrGetSourceFileTaskServ.class);
        serv.getSourceFiles(this.dsTypeName);
        log.debug("数据库日志增量采集线程任务：完成采集");
    }

    @Override
    protected void close() {
        //线程任务完成后 移除提交记录
        IncrGetSourceFileCtrlTask.removeSubmitRecord(this.dsTypeName);
    }
}
