package com.xhw.logcollection.job.thread.tasks;

import com.xhw.logcollection.job.entity.MonitorTask;
import com.xhw.logcollection.job.entity.MonitorTaskRunlog;
import com.xhw.logcollection.job.entity.MonitorWarn;
import com.xhw.logcollection.job.mapper.MonitorTaskMapper;
import com.xhw.logcollection.job.service.MonitorTaskRunlogServ;
import com.xhw.logcollection.job.service.MonitorWarnServ;
import com.xhw.logcollection.job.thread.ctrl.FilePutCtrlTask;
import com.xhw.logcollection.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 抽象任务
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-26
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public abstract class AbstractTask {

    private MonitorTaskRunlog taskRunlog;
    private String taskId;
    private String taskInfo;

    //设置任务ID
    public void setTaskId(String taskId){
        this.taskId = taskId;
    }

    //设置任务信息
    public void setTaskInfo(String taskInfo) {
        this.taskInfo = taskInfo;
    }

    //开始运行
    protected void runStart() {
        taskRunlog = new MonitorTaskRunlog();
        taskRunlog.setRwbh(this.taskId);
        taskRunlog.setTaskinfo(this.taskInfo);
        taskRunlog.setKssj(new Date());

        //保存数据
        try {
            MonitorTaskRunlogServ taskRunlogServ = SpringUtil.getBean(MonitorTaskRunlogServ.class);
            taskRunlogServ.save(taskRunlog);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //运行结束
    protected void runEnd(boolean isSuccess){
        //更新运行结果
        taskRunlog.setJssj(new Date());
        //运行结果，1=成功/2=失败
        if (isSuccess){
            taskRunlog.setYxjg("1");
        } else {
            taskRunlog.setYxjg("2");
        }

        long runTime = taskRunlog.getJssj().getTime()-taskRunlog.getKssj().getTime();
        taskRunlog.setYxsc(BigDecimal.valueOf(runTime /1000));

        //更新运行结果
        try {
            MonitorTaskRunlogServ taskRunlogServ = SpringUtil.getBean(MonitorTaskRunlogServ.class);
            taskRunlogServ.update(taskRunlog);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //保存错误日志
    protected void saveError(Exception e){
        MonitorWarn warn = new MonitorWarn();
        warn.setRunid(taskRunlog.getRunid().longValue());
        warn.setCwlx("1");
        warn.setCwcode("301");
        warn.setCwsj(new Date());
        if(e.getMessage().length() >= 50){
            // 错误信息过长时 截取部分
            String brief = e.getMessage().substring(0, 50).concat("...");
            warn.setCwzy(brief);
        }else{
            warn.setCwzy(e.getMessage());
        }
        warn.setCwxxnr(e.getMessage());
        // 是否告警：1=是，2=否
        warn.setSfgj("1");
        // 是否挂起：1=是，2=否
        warn.setSfgq("2");
        // 是否已处理：1=是，2=否
        warn.setSfycl("2");
        try {
            MonitorWarnServ warnServ = SpringUtil.getBean(MonitorWarnServ.class);
            warnServ.save(warn);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    };

    //错误记录
    protected abstract void logError();
    //业务逻辑
    protected abstract void runBusi() throws Exception;
    //关闭方法
    protected abstract void close();

    protected final void runTask(){
        try {
            runStart();
            runBusi();
            runEnd(true);
        }catch (Exception e){
            e.printStackTrace();
            runEnd(false);
            saveError(e);
            logError();
        }finally{
            close();
        }
    }

}
