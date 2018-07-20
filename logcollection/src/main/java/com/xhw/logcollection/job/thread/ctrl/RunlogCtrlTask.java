package com.xhw.logcollection.job.thread.ctrl;

import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.job.thread.tasks.IncrGetSourceFileTask;
import com.xhw.logcollection.job.thread.tasks.RunlogTask;
import com.xhw.logcollection.job.thread.tasks.TaskStatus;
import com.xhw.logcollection.util.Constant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Future;

/**
 * 系统运行状态任务控制类
 */
public class RunlogCtrlTask implements Runnable {

    private static Log log = LogFactory.getLog(RunlogCtrlTask.class);

    private final String taskId = Constant.TASKID_RUNLOG_TASK;

    //记录 正在采集的任务
    private static Vector<String> submitRunlogTasks;

    @Override
    public void run() {
        //设置任务正在运行
        TaskStatus.statusRunning(taskId);

        List<Future> futures = new ArrayList<>();

        List<Map<String, String>> lst = this.getTasks();
        if(lst != null){
            for (Map<String, String> xtlb : lst) {
                String code = xtlb.get("code");
                //记录提交的拆分任务
                if(!addSubmitRecord(code)){
                    continue;
                }
                //提交任务，按不同的系统源并发采集文件（暂无并发数限制，使用通用线程池即可）
                Future<?> future = ContextBean.commonTaskThreadPool.submit(new RunlogTask(code));
                futures.add(future);
            }
        }

        //设置任务运行完成
        while(true){
            boolean isAllDone = true;
            for(Future future:futures){
                if(!future.isDone()){
                    isAllDone = false;
                }
            }
            if(isAllDone){
                break;
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        //设置任务运行完成
        TaskStatus.statusStopped(taskId);
    }

    public static void init(){
        if(submitRunlogTasks == null){
            submitRunlogTasks = new Vector<>();
        }
    }

    public static boolean addSubmitRecord(String xtlb){
        if(submitRunlogTasks.contains(xtlb)){
            return false;
        }

        //记录提交的任务
        submitRunlogTasks.add(xtlb);
        return true;
    }

    public static boolean removeSubmitRecord(String xtlb){
        return submitRunlogTasks.remove(xtlb);
    }

    //获取 采集Oracle归档日志或在线日志的系统类别（注：可根据不同的系统类别并发采集增量数据的源文件）
    private List<Map<String, String>> getTasks(){
        List<Map<String, String>> xtlbs = ContextBean.getBusCfgGlobalBean().getXtlb(); //10,60
        return xtlbs;
    }
}
