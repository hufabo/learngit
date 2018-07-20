package com.xhw.logcollection.job.service.impl;

import com.xhw.logcollection.job.service.FileResultStatus4IncrServ;
import com.xhw.logcollection.job.ws.FileResultStatusWsServ;
import com.xhw.logcollection.model.dto.FileResultStatusDto;
import com.xhw.logcollection.monitor.entity.BusIncrementFileInfo;
import com.xhw.logcollection.monitor.entity.BusStockFileInfo;
import com.xhw.logcollection.monitor.mapper.BusIncrementFileInfoMapper;
import com.xhw.logcollection.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

/**
 * 增量日志解析文件反馈处理服务接口实现类
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-03-01
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
@Service
public class FileResultStatus4IncrServImpl implements FileResultStatus4IncrServ {

    @Autowired
    private FileResultStatusWsServ wsServ;

    @Autowired
    private BusIncrementFileInfoMapper mapper;
    /**
     * 同步文件入库状态
     *
     * @throws Exception
     */
    @Override
    public void syncFileStatus() throws Exception {
        //从数据库中获取待同步的存量文件状态
        //获取“文件状态” 为“2-上传文件服务器”、“3-上传部局”的日志解析文件
        List<String> statuses = new ArrayList<>();
        statuses.add(Constant.STOCK_FILE_STUS_UPLOAD_SERVER);
        statuses.add(Constant.STOCK_FILE_STUS_UPLOAD_BJ);
        Map<String, Object> params = new HashMap<>();
        params.put("wjzts",statuses);
        List<BusIncrementFileInfo> busIncrFileInfos = mapper.listByCondition(params);
        // 没有记录则退出该方法
        if(busIncrFileInfos.size() == 0) return;

        // 保存文件名清单 用于 接口查询
        List<String> files = new ArrayList<>();
        // 缓存文件本地状态 用于 和接口返回的状态做对比
        Map<String, String> statusCache = new HashMap<>();
        for(BusIncrementFileInfo incrFileInfo:busIncrFileInfos){
            files.add(incrFileInfo.getWjm());
            statusCache.put(incrFileInfo.getWjm(), incrFileInfo.getWjzt());
        }

        //调用webservice接口，批量获取文件入库状态
        String resultXML = wsServ.resultXML(files);

        // TODO 返回测试数据
        resultXML = FileUtil.readResourceFile("/datas/file_result_incr.xml");

        FileResultStatusDto resultStatusDto = (FileResultStatusDto) XmlUtil.xmlToBean(resultXML, FileResultStatusDto.class);
        List<FileResultStatusDto.FileResultBody> resultBodies = resultStatusDto.getBodies();
        // 循环处理返回的结果
        for (FileResultStatusDto.FileResultBody resultBody:resultBodies){
            String fileName = resultBody.getWjm();
            String status = resultBody.getStatus();
            String localStatus = statusCache.get(fileName);
            // 1 安管系统中已更新为“3-上传部局”, 本地数据库中为“2-上传文件服务器”，则将本地“文件状态”更新为“3-上传部局”
            if(Constant.STOCK_FILE_STUS_UPLOAD_BJ.equals(status)
                    && Constant.STOCK_FILE_STUS_UPLOAD_SERVER.equals(localStatus)){
                BusIncrementFileInfo info = new BusIncrementFileInfo();
                Date now = new Date();
                info.setWjm(fileName);
                info.setWjzt(Constant.STOCK_FILE_STUS_UPLOAD_BJ);
                info.setGxsj(now);
                info.setScbjsj(now);
                mapper.updateByFile(info);
            }
            // 2 安管系统中已更新为“4-入库”，则将对应日志解析文件从已传输目录压缩转存至已入库目录，同时将“文件状态”更新为“4-入库”
            if(Constant.STOCK_FILE_STUS_STORAGE.equals(status)){
                String successDir = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_INCR_DIR_SUCCEEDED);
                String backupDir = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_INCR_DIR_BACKUP);
                String tempDir = LoadGlobalPropertiesUtil.getProperty(Constant.FILE_PUT_DIR_TEMP);
                // 压缩文件至临时目录
                String sourceFile = successDir.concat(File.separator).concat(fileName);
                String tempFile = tempDir.concat(File.separator).concat(fileName).concat(".zip");
                ZipUtil.zip(sourceFile, tempFile);
                // 移动临时目录下压缩文件到备份目录
                FileUtil.moveFile(new File(tempFile), backupDir);
                // 保存文件名到redis缓存队列，如果文件数超过最大值，则会返回需要删除的文件名
                String oldFileName = JedisUtil.pushIncrFileName(fileName);
                if(oldFileName != null){
                    String path2Del = backupDir + File.separator + oldFileName + ".zip";
                    boolean b = FileUtil.deleteFile(path2Del);
                }
                // 更新数据库状态
                Date now = new Date();
                BusIncrementFileInfo info = new BusIncrementFileInfo();
                info.setWjm(fileName);
                info.setWjzt(Constant.STOCK_FILE_STUS_STORAGE);
                info.setRksj(now);
                info.setGxsj(now);
                mapper.updateByFile(info);
            }
            // 3 安管系统中已更新为“5-要求重传”, 则更新本地数据库 为 “5-要求重传”
            if(Constant.STOCK_FILE_STUS_RETRANS.equals(status)){
                BusIncrementFileInfo info = new BusIncrementFileInfo();
                info.setWjm(fileName);
                info.setWjzt(Constant.STOCK_FILE_STUS_RETRANS);
                info.setGxsj(new Date());
                mapper.updateByFile(info);
            }
            // TODO 备份文件清理需要后续补充
            // 4 已入库目录用于实现部局已完成入库的日志解析文件的临时备份，
            // 日志采集软件应能根据已入库目录分配的空间大小，
            // 采用“先进先出”原则对其进行清理，确保后续文件能够成功写入
        }

        //记录文件状态（仅仅记录文件状态）
    }
}
