package com.xhw.logcollection.job.thread.ctrl;

import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.job.thread.tasks.IncrGetSourceFileTask;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * 增量源数据文件采集守护线程并发控制任务
 * 描述：采集Oracle归档日志或在线日志文件
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-23
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
@Deprecated
public class IncrGetSourceFileCtrlTask implements Runnable {
    private static Log log = LogFactory.getLog(IncrGetSourceFileCtrlTask.class);

    private volatile static boolean isRunning = false;
    //记录 正在采集的任务
    private static Vector<String> submitGetSourceFileTasks;

    public static boolean getIsRunning() {
        return isRunning;
    }

    public static void setIsRunning(boolean flag) {
        isRunning = flag;
    }


    @Override
    public void run() {
        //设置任务正在运行
        setIsRunning(true);

        System.out.println("==》增量源数据文件采集守护线程并发控制任务");
        this.runJob();

        //设置任务运行完成
        setIsRunning(false);
    }

    //运行任务
    private void runJob(){
        System.out.println("--->>>>增量数据采集守护线程执行任务-------");
        log.debug("守护线程任务：增量数据采集执行任务");
        List<Map<String, String>> lst = this.getTasks();
        if(lst==null){
            log.info("增量源数据文件采集任务：没有待采集的任务");
        }else{
            for (Map<String, String> item : lst) {
                String xtlb = item.get("code");
                //记录提交的拆分任务
                if(!addSubmitRecord(xtlb)){
                    continue;
                }
                //提交任务，按不同的系统源并发采集文件（暂无并发数限制，使用通用线程池即可）
                ContextBean.commonTaskThreadPool.submit(new IncrGetSourceFileTask(xtlb));
            }
        }
    }

    /**
     * 提交记录，记录正在采集的数据源任务
     * @param dsTypeName 数据源类别名称
     * @return true=记录成功，false=记录失败（记录已存在）
            */
    public static boolean addSubmitRecord(String dsTypeName){
        if(submitGetSourceFileTasks.contains(dsTypeName)){
            return false;
        }

        //记录提交的任务
        submitGetSourceFileTasks.add(dsTypeName);

        return true;
    }

    /**
     * 初始化
     */
    public static void init(){
        if(submitGetSourceFileTasks==null){
            submitGetSourceFileTasks = new Vector<>();
        }
    }

    /**
     * 提交记录，移除正在采集的数据源任务
     * @param dsTypeName 数据源类别名称
     */
    public static boolean removeSubmitRecord(String dsTypeName){
        return submitGetSourceFileTasks.remove(dsTypeName);
    }

    //获取 采集Oracle归档日志或在线日志的系统类别（注：可根据不同的系统类别并发采集增量数据的源文件）
    private List<Map<String, String>> getTasks(){
        List<Map<String, String>> xtlbs = ContextBean.getBusCfgGlobalBean().getXtlb();//10,60
        return xtlbs;
    }
}
