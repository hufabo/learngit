package com.xhw.logcollection.job.thread.tasks;

import com.xhw.logcollection.job.service.UpdateStrategyServ;
import com.xhw.logcollection.util.Constant;
import com.xhw.logcollection.util.SpringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 采集策略更新线程任务
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-23
 * @note 单线程任务
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public class UpdateStrategyTask extends AbstractTask implements Runnable {
    private static Log log = LogFactory.getLog(UpdateStrategyTask.class);
    //任务编号=03
    private final String taskId= Constant.TASKID_UPDATE_STRATEGY_TASK;

    public UpdateStrategyTask(){
        this.setTaskId(this.taskId); //设置任务编号
        this.setTaskInfo(String.format("更新采集策略任务"));
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
        log.debug("采集策略更新线程任务：开始更新策略和采集任务");
        UpdateStrategyServ updateStrategyServ =
                SpringUtil.getBean(UpdateStrategyServ.class);
        updateStrategyServ.updateStrategy();

        //1、调用webservice接口获取策略数据
        //2、将策略更新到本地数据库
        log.debug("采集策略更新线程任务：完成更新策略和采集任务");
    }

    @Override
    protected void close() {
        //设置任务运行完成
        TaskStatus.statusStopped(taskId);
    }
}
