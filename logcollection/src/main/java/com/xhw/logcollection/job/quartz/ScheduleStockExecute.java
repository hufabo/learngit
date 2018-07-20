package com.xhw.logcollection.job.quartz;

import com.xhw.logcollection.job.entity.BusCfgGlobalBean;
import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.job.thread.ctrl.StockExecuteCtrlTask;
import com.xhw.logcollection.job.thread.tasks.TaskStatus;
import com.xhw.logcollection.util.Constant;
import com.xhw.logcollection.util.DateUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Author 孔纲
 * @Date 2018/3/30
 */
public class ScheduleStockExecute implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        int yxzt = TaskStatus.getYXZT(Constant.TASKID_STOCK_EXECUTE_TASK);
        int sfzd = TaskStatus.getSFZD(Constant.TASKID_STOCK_EXECUTE_TASK);
        if(sfzd == 1 & yxzt == 1){
            StockExecuteCtrlTask.init();
            //存量采集时间段判断
            int currHours = DateUtil.dateNow().getHours();
            BusCfgGlobalBean busCfgGlobalBean = ContextBean.getBusCfgGlobalBean();
            if(currHours>=busCfgGlobalBean.getClrwqdsj()
                    && currHours<=busCfgGlobalBean.getClrwjssj()) {
                ContextBean.commonTaskThreadPool.submit(new StockExecuteCtrlTask());
            }
        }
    }
}
