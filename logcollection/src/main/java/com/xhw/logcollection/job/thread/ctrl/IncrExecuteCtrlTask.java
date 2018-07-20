package com.xhw.logcollection.job.thread.ctrl;

import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.job.thread.tasks.IncrExecuteTask;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * （增量）控制数据库日志文件解析线程并发控制任务
 * 描述：解析Oracle归档日志或在线日志文件
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-23
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
@Deprecated
public class IncrExecuteCtrlTask implements Runnable {
    private static Log log = LogFactory.getLog(IncrExecuteCtrlTask.class);

    private volatile static boolean isRunning = false;
    //记录 正在解析的任务
    private static Vector<String> submitParseLogFileTasks;

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

        System.out.println("==》增量数据采集任务：日志文件解析线程并发控制任务");
        this.runJob();

        //设置任务运行完成
        setIsRunning(false);
    }

    //运行任务
    private void runJob(){
        System.out.println("--->>>>增量数据采集任务：日志文件解析线程并发控制任务-------");
        log.debug("增量数据采集任务：日志文件解析线程并发控制任务");
        List<Map<String, String>> lst = this.getTasks();
        if(lst==null){
            log.info("增量数据采集任务：没有待解析的任务");
        }else{
            for (Map<String, String> dsTypeName : lst) {
                String code = dsTypeName.get("code");
                //记录提交的任务
                if(!addSubmitRecord(code)){
                    continue;
                }
                //提交任务，按不同的系统源并发采集文件（暂无并发数限制，使用通用线程池即可）
                ContextBean.commonTaskThreadPool.submit(new IncrExecuteTask(code));
            }
        }
    }

    /**
     * 提交记录，记录正在解析的任务
     * @param dsTypeName 数据源类别名称
     * @return true=记录成功，false=记录失败（记录已存在）
     */
    public static boolean addSubmitRecord(String dsTypeName){
        if(submitParseLogFileTasks.contains(dsTypeName)){
            return false;
        }

        //记录提交的任务
        submitParseLogFileTasks.add(dsTypeName);

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
     * @param dsTypeName 数据源类别名称
     */
    public static boolean removeSubmitRecord(String dsTypeName){
        return submitParseLogFileTasks.remove(dsTypeName);
    }

    //获取 根据不同的系统类别解析Oracle归档日志或在线日志（注：可根据不同的系统类别并发采数据库日志文件）
    private  List<Map<String, String>> getTasks(){
        List<Map<String, String>> xtlbs = ContextBean.getBusCfgGlobalBean().getXtlb();//10,60
        return xtlbs;
    }
}
