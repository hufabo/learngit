package com.xhw.logcollection.job.thread.ctrl;

import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.job.thread.tasks.StockExecuteTask;
import com.xhw.logcollection.job.thread.tasks.TaskStatus;
import com.xhw.logcollection.monitor.entity.BusStockBreakPointInfo;
import com.xhw.logcollection.monitor.mapper.BusStockBreakPointInfoMapper;
import com.xhw.logcollection.monitor.service.BusStockDealStatusServ;
import com.xhw.logcollection.util.Constant;
import com.xhw.logcollection.util.SpringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 存量数据采集线程并发控制任务
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-23
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public class StockExecuteCtrlTask implements Runnable {

    private static Log log = LogFactory.getLog(StockExecuteCtrlTask.class);

    private final String taskId = Constant.TASKID_STOCK_EXECUTE_TASK;

    //记录 正在采集的任务
    private static Vector<String> submitExecuteTasks;

    //存量数据采集线程池
    private static Map<String, ExecutorService> poolThread;

    @Override
    public void run() {
        //设置任务正在运行
        TaskStatus.statusRunning(taskId);

        List<Future> futures = new ArrayList<>();

        System.out.println("==》存量数据采集线程并发控制任务");
        //1、从存量断点表获取存量采集任务
        List<BusStockBreakPointInfo> lst = this.getTasks();
        Set<String> xtlbs = new HashSet<>();
        Set<String> bms = new HashSet<>();
        if(lst==null){
            log.info("存量数据采集线程断点任务：没有待采集的断点任务");
        }else{
            for (BusStockBreakPointInfo beanTask : lst) {
                //记录提交的断点任务
                if(!addSubmitRecord(beanTask)){
                    continue;
                }
                xtlbs.add(beanTask.getJgxtlb());
                bms.add(beanTask.getBm());
                //提交断点任务（
                Future<?> future = StockExecuteCtrlTask.getPoolThread(beanTask.getJgxtlb()).submit(new StockExecuteTask(beanTask));
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

        // 采集任务执行完毕，统计采集数据总量和采集文件数等信息
        // 根据 存量文件表 更新 存量数据处理状态表
        Map<String, Object> params = new HashMap<>();
        params.put("xtlbs",xtlbs);
        params.put("bms",bms);
        BusStockDealStatusServ stockDealStatusServ = SpringUtil.getBean(BusStockDealStatusServ.class);
        stockDealStatusServ.updateCJWJS(params);

        //设置任务运行完成
        TaskStatus.statusStopped(taskId);
    }

    /**
     * 初始化
     */
    public static void init(){
        //初始化 采集集合
        if(submitExecuteTasks==null){
            submitExecuteTasks = new Vector<>();
        }
    }

    /**
     * 正在采集的提交记录
     * @param beanTeask 断点任务
     * @return true=记录成功，false=记录失败（记录已存在）
     */
    public static boolean addSubmitRecord(BusStockBreakPointInfo beanTeask){
        String taskName = beanTeask.getJgxtlb() + beanTeask.getBm() + beanTeask.getSjkbh();
        if(submitExecuteTasks.contains(taskName)){
            return false;
        }

        //记录上传的文件
        submitExecuteTasks.add(taskName);
        return true;
    }

    /**
     * 移除正在采集的提交记录
     * @param beanTeask 断点任务
     */
    public static boolean removeSubmitRecord(BusStockBreakPointInfo beanTeask){
        String taskName = beanTeask.getJgxtlb() + beanTeask.getBm() + beanTeask.getSjkbh();
        return submitExecuteTasks.remove(taskName);
    }

    public synchronized static ExecutorService getPoolThread(String xtlb){
        //存量数据采集线程池
        if(poolThread == null){
            poolThread = new HashMap<>();
        }
        ExecutorService executorService = poolThread.get(xtlb);
        if(executorService == null){
            int poolSize =Long.valueOf(ContextBean.getBusCfgGlobalBean().getClsjkzdz()).intValue();
            executorService = Executors.newFixedThreadPool(poolSize);
            poolThread.put(xtlb, executorService);
        }
        return executorService;
    }

    //获取存量断点任务
    private List<BusStockBreakPointInfo> getTasks(){
        //从数据库中获取待采集的存量任务
        //  表名：bus_stock_break_point_info=存量数据块断点表
        //  条件：WCBJ(完成标记，0-未完成，1-已完成)=0
        BusStockBreakPointInfoMapper stockBreakPointInfoMapper = SpringUtil.getBean(BusStockBreakPointInfoMapper.class);
        Map<String, Object> params = new HashMap<>();
        params.put("wcbj","0");
        List<BusStockBreakPointInfo> lst = stockBreakPointInfoMapper.listByCondition(params);
        return lst;
    }
}
