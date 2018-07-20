package com.xhw.logcollection.job.service.impl;

import com.xhw.logcollection.job.service.FileRePut4StockServ;
import com.xhw.logcollection.job.service.StockExecuteTaskServ;
import com.xhw.logcollection.monitor.entity.BusStockBreakPointInfo;
import com.xhw.logcollection.monitor.entity.BusStockFileInfo;
import com.xhw.logcollection.monitor.mapper.BusStockBreakPointInfoMapper;
import com.xhw.logcollection.monitor.mapper.BusStockFileInfoMapper;
import com.xhw.logcollection.util.Constant;
import com.xhw.logcollection.util.FileUtil;
import com.xhw.logcollection.util.LoadGlobalPropertiesUtil;
import com.xhw.logcollection.util.ZipUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 存量错误数据重传任务服务接口实现类
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-03-02
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
@Service
public class FileRePut4StockServImpl implements FileRePut4StockServ {

    @Autowired
    private BusStockFileInfoMapper mapper;

    @Autowired
    private BusStockBreakPointInfoMapper breakPointInfoMapper;

    @Autowired
    private StockExecuteTaskServ stockExecuteTaskServ;

    /**
     * 重传数据
     *
     * @throws Exception
     */
    @Override
    public void fileRePut() throws Exception {
        //1、获取待重传存量数据任务（WJZT = 5要求重传）
        HashMap<String, Object> params = new HashMap<>();
        params.put("wjzt", Constant.STOCK_FILE_STUS_RETRANS);
        List<BusStockFileInfo> stockFileInfos = mapper.listByCondition(params);
        //2、判断文件是否存在
        // 2.1 若文件存在，则将备份目录中的文件移到待上传的日志解析目录中（由“文件上传任务”负责文件重传），更新存量数据文件表状态（WJZT=2-上传文件服务器）
        // 2.2 文件若不存在，则需要发起 重新采集（重置“存量数据块断点表”的WCBJ = 0-未完成），并更新存量数据文件表状态（WJZT=6-本地文件丢失）
        //   采集任务处理：采集数据不存在等原因 更新状态（WJZT=7-数据丢失），并记录错误信息
        String targetDir = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_STOCK_DIR_AWAIT);
        for(BusStockFileInfo fileInfo:stockFileInfos){
            String fileName = fileInfo.getWjm();
            File file = searchStockFile(fileName);
            if(file != null){
                FileUtil.moveFile(file, targetDir);
                // 更新数据库状态
                BusStockFileInfo stockFileInfo = new BusStockFileInfo();
                stockFileInfo.setWjm(fileName);
                // WJZT=1-采集，上传完成后改为2
                stockFileInfo.setWjzt(Constant.STOCK_FILE_STUS_COLLECTED);
                stockFileInfo.setGxsj(new Date());
                mapper.updateFileInfo(stockFileInfo);
            }else{
                // 更新数据库状态
                BusStockFileInfo stockFileInfo = new BusStockFileInfo();
                stockFileInfo.setWjm(fileName);
                stockFileInfo.setWjzt(Constant.STOCK_FILE_STUS_FILE_MISSING);
                stockFileInfo.setGxsj(new Date());
                mapper.updateFileInfo(stockFileInfo);
            }
        }

        // 获取文件状态-6 本地文件丢失，启动重新采集过程
        params = new HashMap<>();
        params.put("wjzt", Constant.STOCK_FILE_STUS_FILE_MISSING);
        List<BusStockFileInfo> lostFiles = mapper.listByCondition(params);
        for(BusStockFileInfo file:lostFiles){
            // 发起重采流程，成功后状态更新为1-采集 失败后更新为7-数据丢失
            stockExecuteTaskServ.executeByFile(file.getWjm());
        }
    }

    /**
     * 根据文件名到上传成功目录或者备份目录搜索是否有该文件
     * 找到文件返回文件对象，否则返回null
     * @author konggang
     * @date 2018/3/10 9:14
     */
    private File searchStockFile(String fileName){
        // 先从成功目录里面找
        String successDir = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_STOCK_DIR_SUCCEEDED);
        File file = new File(successDir.concat(File.separator).concat(fileName));
        if(file.exists()){
            return file;
        }else{
            // 备份目录的文件是zip打包格式的
            // 打包文件名默认就是文件名 + ".zip"
            String zipFileName = fileName.concat(".zip");
            String backupDir = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_STOCK_DIR_BACKUP);
            File zipFile = new File(backupDir.concat(File.separator).concat(zipFileName));
            // 如果找到打包文件 需要解压到临时目录
            if(zipFile.exists()){
                String tempDir = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_DIR_TEMP);
                // 解压
                ZipUtil.unzip(zipFile.getPath(),tempDir);
                File tempFile = new File(tempDir.concat(File.separator).concat(fileName));
                return tempFile;
            }
        }
        return null;
    }
}
