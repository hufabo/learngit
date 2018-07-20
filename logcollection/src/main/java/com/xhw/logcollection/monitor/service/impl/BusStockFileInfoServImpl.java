package com.xhw.logcollection.monitor.service.impl;

import com.xhw.logcollection.monitor.entity.BusStockFileInfo;
import com.xhw.logcollection.monitor.mapper.BusStockFileInfoMapper;
import com.xhw.logcollection.monitor.service.BusStockFileInfoServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 存量数据断点信息服务实现
 * @version 0.0.1
 * @Author wanghaiyang
 * @Description
 * @Date 2018/2/28 17:21
 * @Modified By:
 */
@Service
public class BusStockFileInfoServImpl implements BusStockFileInfoServ {

    @Autowired
    private BusStockFileInfoMapper stockFileInfoMapper;

    /**
     * 更新存量文件信息
     *
     * @param busStockFileInfo
     * @Author wanghaiyang
     * @Date 2018/3/2 16:15
     */
    @Override
    public boolean updateFileInfo(BusStockFileInfo busStockFileInfo) {
        return stockFileInfoMapper.updateFileInfo(busStockFileInfo);
    }

    @Override
    public List<String> getStockFilePutList() {
        Map<String, Object> params = new HashMap<>();
        params.put("wjzt","1"); // 文件状态。1-采集，2-上传文件服务器，3-上传部局，4-入库，5要求重传，6-本地文件丢失，7-数据丢失
        List<BusStockFileInfo> stockFileInfos = stockFileInfoMapper.getUploadList(params);
        List<String> fileNames = new ArrayList<>();
        for(BusStockFileInfo fileInfo:stockFileInfos){
            fileNames.add(fileInfo.getWjm());
        }
        return fileNames;
    }

}
