package com.xhw.logcollection.monitor.service;

import com.xhw.logcollection.monitor.entity.BusStockFileInfo;

import java.util.List;

/**
 * @version 0.0.1
 * @Author wanghaiyang
 * @Description
 * @Date 2018/2/28 17:16
 * @Modified By:
 */
public interface BusStockFileInfoServ {

    /**
     * 更新存量文件信息
     * @Author wanghaiyang
     * @param  busStockFileInfo
     * @Date 2018/3/2 16:15
     */
    boolean updateFileInfo(BusStockFileInfo busStockFileInfo);

    /**
     * 获取待上传的存量文件列表
     * @author konggang
     * @date 2018/3/21 11:34
     */
    List<String> getStockFilePutList();
}
