package com.xhw.logcollection.job.service.impl;

import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.job.service.FilePut4StockServ;
import com.xhw.logcollection.monitor.entity.BusStockFileInfo;
import com.xhw.logcollection.monitor.service.BusStockFileInfoServ;
import com.xhw.logcollection.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;

/**
 * 存量文件上传任务接口实现类
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-03-01
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
@Service
public class FilePut4StockServImpl implements FilePut4StockServ {

    private static Logger logger = LoggerFactory.getLogger(FilePut4StockServImpl.class);

    @Autowired
    private BusStockFileInfoServ stockFileInfoServ;

    /**
     * 推送指定的文件
     * @param fileName 文件名
     * @throws Exception
     */
    @Override
    public void putFile(String fileName) throws Exception {
        logger.info("==》存量日志解析文件传输任务，文件名=" + fileName);

        //1、获取上传的文件
        String fileDir = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_STOCK_DIR_AWAIT);
        File file = FileUtil.getFile(fileDir, fileName);

        //3、上传文件,更新状态
        String type = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_TYPE);
        String bathPath = ContextBean.getBusCfgGlobalBean().getClscml();
        if("ftp".equals(type)){
            String host = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_FTP_HOST);
            String port = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_FTP_PORT);
            String username = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_FTP_USERNAME);
            String password = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_FTP_PASSWORD);
            InputStream is = new FileInputStream(file);
            boolean b = FtpUtil.uploadFile(host, Integer.parseInt(port), username, password, bathPath, null, fileName, is);
            is.close();
            if(!b){
                throw new RuntimeException("上传失败！");
            }

        }else{
            String url = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_NFS_URL);
            String remoteUrl = url.concat("/").concat(bathPath).concat("/").concat(fileName);
            String localUrl = fileDir.concat(File.separator).concat(fileName);
            NFSUtils.nfsPutFile(remoteUrl, localUrl);
        }

        //更新数据库状态
        BusStockFileInfo fileInfo = new BusStockFileInfo();
        fileInfo.setWjm(fileName);
        fileInfo.setWjzt(Constant.STOCK_FILE_STUS_UPLOAD_SERVER);
        fileInfo.setScfwqsj(new Date());
        stockFileInfoServ.updateFileInfo(fileInfo);
    }

}
