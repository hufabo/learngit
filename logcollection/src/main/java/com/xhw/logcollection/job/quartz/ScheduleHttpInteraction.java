package com.xhw.logcollection.job.quartz;

import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.job.thread.tasks.HttpInteractionTask;
import com.xhw.logcollection.job.thread.tasks.TaskStatus;
import com.xhw.logcollection.util.Constant;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Author 孔纲
 * @Date 2018/4/1
 */
public class ScheduleHttpInteraction implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        int yxzt = TaskStatus.getYXZT(Constant.TASKID_HTTP_INTERACTION_TASK);
        int sfzd = TaskStatus.getSFZD(Constant.TASKID_HTTP_INTERACTION_TASK);
        if(sfzd == 1 && yxzt == 1){
            ContextBean.commonTaskThreadPool.submit(new HttpInteractionTask());
        }
    }
}
