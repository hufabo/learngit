package com.xhw.logcollection.job.thread.ctrl;

import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.job.thread.tasks.IncrExecuteTask;
import com.xhw.logcollection.job.thread.tasks.IncrIntegratedTask;
import com.xhw.logcollection.job.thread.tasks.TaskStatus;
import com.xhw.logcollection.util.Constant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Future;

public class IncrIntegratedCtrlTask implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(IncrIntegratedCtrlTask.class);

    private final String taskId = Constant.TASKID_INCR_INTEGRATED_TASK;

    //记录 正在解析的任务
    private static Vector<String> submitParseLogFileTasks;

    @Override
    public void run() {
        //设置任务正在运行
        TaskStatus.statusRunning(taskId);

        this.runJob();

        //设置任务运行完成
        TaskStatus.statusStopped(taskId);
    }

    //运行任务
    private void runJob(){
        List<Future> futures = new ArrayList<>();

        List<Map<String, String>> lst = this.getTasks();
        if(lst==null){
            logger.info("增量数据采集任务：没有待解析的任务");
        }else{
            for (Map<String, String> xtlb : lst) {
                String code = xtlb.get("code");
                //记录提交的任务
                if(!addSubmitRecord(code)){
                    continue;
                }
                //提交任务，按不同的系统源并发采集文件（暂无并发数限制，使用通用线程池即可）
                Future<?> future = ContextBean.commonTaskThreadPool.submit(new IncrIntegratedTask(code));
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
    }

    /**
     * 提交记录，记录正在解析的任务
     * @param xtlb 数据源类别名称
     * @return true=记录成功，false=记录失败（记录已存在）
     */
    public static boolean addSubmitRecord(String xtlb){
        if(submitParseLogFileTasks.contains(xtlb)){
            return false;
        }

        //记录提交的任务
        submitParseLogFileTasks.add(xtlb);

        return true;
    }

    /**
     * 初始化
     */
    public static void init(){
        if(submitParseLogFileTasks==null){
            submitParseLogFileTasks = new Vector<>();
        }
    }

    /**
     * 提交记录，移除正在解析的任务
     * @param xtlb 数据源类别名称
     */
    public static boolean removeSubmitRecord(String xtlb){
        return submitParseLogFileTasks.remove(xtlb);
    }

    //获取 根据不同的系统类别解析Oracle归档日志或在线日志（注：可根据不同的系统类别并发采数据库日志文件）
    private  List<Map<String, String>> getTasks(){
        List<Map<String, String>> xtlbs = ContextBean.getBusCfgGlobalBean().getXtlb();//10,60
        return xtlbs;
    }
}
