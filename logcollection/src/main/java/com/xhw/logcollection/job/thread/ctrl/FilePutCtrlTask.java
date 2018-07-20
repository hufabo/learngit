package com.xhw.logcollection.job.thread.ctrl;

import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.job.thread.tasks.FilePutTask;
import com.xhw.logcollection.job.thread.tasks.TaskStatus;
import com.xhw.logcollection.monitor.entity.BusIncrementFileInfo;
import com.xhw.logcollection.monitor.service.BusIncrementFileInfoServ;
import com.xhw.logcollection.monitor.service.BusStockFileInfoServ;
import com.xhw.logcollection.util.Constant;
import com.xhw.logcollection.util.LoadGlobalPropertiesUtil;
import com.xhw.logcollection.util.SpringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 日志解析文件传输线程控制任务
 * 备注：控制文件的并发上传
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-23
 * @note 单线程任务
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public class FilePutCtrlTask implements Runnable {
    private static Log log = LogFactory.getLog(FilePutCtrlTask.class);

    //任务编号=01
    private final String taskId = Constant.TASKID_FILE_PUT_TASK;

    //日志解析文件上传线程池
    private static ExecutorService poolThread;

    //记录 正在上传的文件记录
    private static Vector<String> uploadingFiles;

    @Override
    public void run() {
        //设置任务正在运行
        TaskStatus.statusRunning(taskId);

        List<Future> futures = new ArrayList<>();

        try {
            //处理思路：
            //1、从文件传输目录获取待上传的文件（区分存量文件还是增量文件）
            //2、控制文件上传并发量（调用FilePutTask线程类来传输文件）

            //提交存量上传任务
            this.submitStockFileTask(futures);

            //提交增量上传任务
            this.submitIncrFileTask(futures);
        }catch (Exception e){
            e.printStackTrace();
            log.error("日志解析文件传输分发任务运行失败！详细错误："+e.getMessage());
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

        TaskStatus.statusStopped(taskId);
    }

    //获取待上传的存量文件
    private List<String> lstStockFile () throws Exception{
        BusStockFileInfoServ stockFileInfoServ = SpringUtil.getBean(BusStockFileInfoServ.class);
        List<String> fileNameList = stockFileInfoServ.getStockFilePutList();
        return fileNameList;
    }

    //获取待上传的增量文件
    private List<String> lstIncrFile () throws Exception{
        // 从增量文件表获取待上传的文件列表
        BusIncrementFileInfoServ fileInfoServ = SpringUtil.getBean(BusIncrementFileInfoServ.class);
        List<String> fileNameList = fileInfoServ.getIncrFilePutList();
        return fileNameList;
    }

    //提交存量上传任务
    private void submitStockFileTask(List<Future> futures) throws Exception{
        List<String> lstStockFile = this.lstStockFile();
        if(lstStockFile!=null){
            for (String fileName : lstStockFile) {
                //记录上传的文件
                if(!addSubmitRecord(fileName)){
                    continue;
                }

                //提交文件上传任务，文件类型：1=存量文件，2=增量文件
                Future<?> future = poolThread.submit(new FilePutTask(1, fileName));
                futures.add(future);
            }
        }
    }

    //提交增量上传任务
    private void submitIncrFileTask(List<Future> futures) throws Exception{
        List<String> lstIncrFile = this.lstIncrFile();
        if(lstIncrFile!=null){
            for (String fileName : lstIncrFile) {
                //记录上传的文件
                if(!addSubmitRecord(fileName)){
                    continue;
                }

                //提交文件上传任务，文件类型：1=存量文件，2=增量文件
                Future<?> future = poolThread.submit(new FilePutTask(2, fileName));
                futures.add(future);
            }
        }
    }

    /**
     * 添加文件上传的提交记录
     * 备注：文件上传成功或失败后都需要相应的移除上传记录
     * @param fileName 文件名
     * @return true=记录成功，false=记录失败（记录已存在）
     */
    public static boolean addSubmitRecord(String fileName){
        if(uploadingFiles.contains(fileName)){
            return false;
        }

        //记录上传的文件
        uploadingFiles.add(fileName);

        return true;
    }

    /**
     * 移除文件上传的提交记录
     * @param fileName
     */
    public static boolean removeSubmitRecord(String fileName){
        return uploadingFiles.remove(fileName);
    }


    /**
     * 初始化
     */
    public static void init(){
        //日志解析文件上传线程池
        if(poolThread==null){
            int poolSize = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_MAX_THREADS,5);
            poolThread = Executors.newFixedThreadPool(poolSize);
        }
        if(uploadingFiles==null){
            uploadingFiles = new Vector<>();
        }
    }
}
