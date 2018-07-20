package com.xhw.logcollection.job.quartz;

import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.job.thread.ctrl.IncrGetSourceFileCtrlTask;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Author 孔纲
 * @Date 2018/4/1
 */
@Deprecated
public class ScheduleIncrGetSourceFile implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        IncrGetSourceFileCtrlTask.init();
        ContextBean.commonTaskThreadPool.submit(new IncrGetSourceFileCtrlTask());
    }
}
