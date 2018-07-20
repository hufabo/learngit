package com.xhw.logcollection.job.thread.tasks;

import com.xhw.logcollection.job.service.IncrExecuteTaskServ;
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
@Deprecated
public class IncrExecuteTask extends AbstractTask implements Runnable {
    private static Log log = LogFactory.getLog(IncrExecuteTask.class);
    //任务编号=07
    private final String taskId= Constant.TASKID_INCR_EXECUTE_TASK;
    private String dsTypeName;

    public IncrExecuteTask( String dsTypeName){
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
        System.out.println("==》增量数据采集线程执行任务");
        log.debug("数据库日志增量采集线程任务：开始采集");
        IncrExecuteTaskServ serv = SpringUtil.getBean(IncrExecuteTaskServ.class);
        serv.parseFiles(this.dsTypeName);
        log.debug("数据库日志增量采集线程任务：完成采集");
    }

    @Override
    protected void close() {

    }
}
