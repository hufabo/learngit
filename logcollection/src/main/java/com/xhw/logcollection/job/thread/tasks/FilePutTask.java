package com.xhw.logcollection.job.thread.tasks;

import com.xhw.logcollection.job.service.FilePut4IncrServ;
import com.xhw.logcollection.job.service.FilePut4StockServ;
import com.xhw.logcollection.job.thread.ctrl.FilePutCtrlTask;
import com.xhw.logcollection.job.thread.ctrl.StockExecuteCtrlTask;
import com.xhw.logcollection.util.Constant;
import com.xhw.logcollection.util.FileUtil;
import com.xhw.logcollection.util.LoadGlobalPropertiesUtil;
import com.xhw.logcollection.util.SpringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 日志解析文件传输线程任务
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-23
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
public class FilePutTask extends AbstractTask implements Runnable {
    private static Log log = LogFactory.getLog(FilePutTask.class);

    //任务编号=01
    private final String taskId= Constant.TASKID_FILE_PUT_TASK;
    //文件名
    private String fileName;
    //文件类型：1=存量文件，2=增量文件
    private int fileType;

    /**
     * @param fileType 文件类型：1=存量文件，2=增量文件
     * @param fileName 文件名
     */
    public FilePutTask (int fileType, String fileName){
        this.setTaskId(this.taskId); //设置任务编号
        this.fileType = fileType;
        this.fileName = fileName;

        String fileTypeName;
        if(fileType == 1){
            fileTypeName = "存量文件";
        }else{
            fileTypeName = "增量文件";
        }
        this.setTaskInfo(String.format("文件上传任务, 文件类型%s, 文件名%s", fileTypeName, fileName));
    }

    @Override
    public void run() {
        this.runTask();
    }

    //错误记录
    @Override
    public void logError(){
        //记录运行错误信息
        try {
            if (this.fileType == 1) {
                //操作失败，则将文件移到“文件上传失败的目录中”
                String fileDir = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_STOCK_DIR_AWAIT);
                String targetDir = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_STOCK_DIR_FAILED);
                FileUtil.moveFile(fileDir, targetDir, this.fileName);

            } else if (this.fileType == 2) {
                //操作失败，则将文件移到“文件上传失败的目录中”
                String fileDir = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_INCR_DIR_AWAIT);
                String targetDir = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_INCR_DIR_FAILED);
                FileUtil.moveFile(fileDir, targetDir, this.fileName);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            log.error("文件备份失败！详细错误："+ex.getMessage());
        }
    }
    //业务逻辑
    @Override
    public void runBusi() throws Exception{
        log.debug("日志解析文件传输任务：开始传输任务[文件名="+fileName+"]");
        if(this.fileType == 1) {
            FilePut4StockServ stockServ = SpringUtil.getBean(FilePut4StockServ.class);
            stockServ.putFile(this.fileName);

            //操作成功，则将文件移到“文件上传成功的目录中”
            String fileDir = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_STOCK_DIR_AWAIT);
            String targetDir = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_STOCK_DIR_SUCCEEDED);
            FileUtil.moveFile(fileDir, targetDir, this.fileName);

        } else if(this.fileType == 2) {
            FilePut4IncrServ incrServ = SpringUtil.getBean(FilePut4IncrServ.class);
            incrServ.putFile(this.fileName);

            //操作成功，则将文件移到“文件上传成功的目录中”
            String fileDir = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_INCR_DIR_AWAIT);
            String targetDir = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_INCR_DIR_SUCCEEDED);
            FileUtil.moveFile(fileDir, targetDir, this.fileName);
        }
        log.debug("日志解析文件传输任务：完成传输任务[文件名="+fileName+"]");
    }

    @Override
    protected void close() {
        //文件上传任务完成，移除提交记录
        FilePutCtrlTask.removeSubmitRecord(this.fileName);
    }
}
