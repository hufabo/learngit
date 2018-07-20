package com.xhw.logcollection.job.quartz;

import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.job.thread.ctrl.IncrExecuteCtrlTask;
import com.xhw.logcollection.job.thread.tasks.TaskStatus;
import com.xhw.logcollection.util.Constant;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Author 孔纲
 * @Date 2018/4/1
 */
@Deprecated
public class ScheduleIncrExecute implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        int status = TaskStatus.getYXZT(Constant.TASKID_INCR_EXECUTE_TASK);
        if(status == 1){
            IncrExecuteCtrlTask.init();
            ContextBean.commonTaskThreadPool.submit(new IncrExecuteCtrlTask());
        }
    }
}
