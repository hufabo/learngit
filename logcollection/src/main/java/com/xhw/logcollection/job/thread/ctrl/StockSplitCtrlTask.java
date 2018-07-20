package com.xhw.logcollection.job.thread.ctrl;

import com.xhw.logcollection.job.entity.BusCfgTask;
import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.job.mapper.BusCfgTaskMapper;
import com.xhw.logcollection.job.thread.tasks.StockSplitTask;
import com.xhw.logcollection.job.thread.tasks.TaskStatus;
import com.xhw.logcollection.job.thread.tasks.UpdateStrategyTask;
import com.xhw.logcollection.util.Constant;
import com.xhw.logcollection.util.SpringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 存量数据采集拆分线程并发控制任务
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-23
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public class StockSplitCtrlTask implements Runnable {

    private static Log log = LogFactory.getLog(StockSplitCtrlTask.class);

    private final String taskId= Constant.TASKID_STOCK_SPLIT_TASK;

    //记录 正在拆分的任务
    private static Vector<String> submitSplitTasks;

    @Override
    public void run() {
        //设置任务正在运行
        TaskStatus.statusRunning(taskId);

        List<Future> futures = new ArrayList<>();

        System.out.println("==》存量数据采集线程并发控制任务");
        //1、获取采集任务
        List<BusCfgTask> lst = this.getTasks();
        if(lst==null){
            log.info("存量数据采集线程拆分任务：没有待拆分的存量采集任务");
        }else{
            for (BusCfgTask beanTask : lst) {
                System.out.println("表名:" + beanTask.getBm());
                //记录提交的拆分任务
                if(!addSubmitRecord(beanTask)){
                    continue;
                }
                //提交拆分任务（注意：都会占用数据库资源，所以采用了同采集任务的线程池）
                Future<?> future = StockExecuteCtrlTask.getPoolThread(beanTask.getJgxtlb()).submit(new StockSplitTask(beanTask));
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

    /**
     * 正在拆分的提交记录
     * @param beanTeask 存量任务
     * @return true=记录成功，false=记录失败（记录已存在）
     */
    public static boolean addSubmitRecord(BusCfgTask beanTeask){
        String taskName = beanTeask.getJgxtlb()+beanTeask.getBm();
        if(submitSplitTasks.contains(taskName)){
            return false;
        }

        //记录上传的文件
        submitSplitTasks.add(taskName);

        return true;
    }

    /**
     * 移除正在拆分的提交记录
     * @param beanTeask 存量任务
     */
    public static boolean removeSubmitRecord(BusCfgTask beanTeask){
        String taskName = beanTeask.getJgxtlb()+beanTeask.getBm();
        return submitSplitTasks.remove(taskName);
    }

    /**
     * 初始化
     */
    public static void init(){
        //初始化 正在拆分的任务 集合
        if(submitSplitTasks==null){
            submitSplitTasks = new Vector<>();
        }
    }

    //获取采集
    private List<BusCfgTask> getTasks(){
        List<BusCfgTask> lst = new ArrayList<>();
        //从数据库中获取待采集的存量任务
        //  表名：bus_cfg_task=单表日志采集参数表
        //  条件：CLCJBJ（存量数据采集标记，0-不采集，1-采集）=1
        //      CLWCBJ(存量数据采集完成标记。0-未启动，1-正在执行，2-已完成)=0
        BusCfgTaskMapper cfgTaskMapper = SpringUtil.getBean(BusCfgTaskMapper.class);
        Map<String, Object> params = new HashMap<>();
        params.put("clcjbj","1");
        params.put("clwcbj","0");
        List<BusCfgTask> cltasks = cfgTaskMapper.listByCondition(params);
        return cltasks;
    }
}
