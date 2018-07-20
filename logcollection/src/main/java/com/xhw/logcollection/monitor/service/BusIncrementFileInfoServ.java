package com.xhw.logcollection.monitor.service;

import com.github.pagehelper.PageInfo;
import com.xhw.logcollection.monitor.entity.BusIncrementFileInfo;

import java.util.List;
import java.util.Map;

/**
 * @version 0.0.1
 * @Author wanghaiyang
 * @Description
 * @Date 2018/2/28 17:16
 * @Modified By:
 */
public interface BusIncrementFileInfoServ {
    /**
     * 根据条件获取增量断点文件列表
     * @Author wanghaiyang
     * @param  paraMap
     * @Date 2018/3/1 14:12
     */
    PageInfo<BusIncrementFileInfo> listByCondition(Map<String,Object> paraMap);

    /**
     * 根据状态获取增量文件列表
     * @Author wanghaiyang
     * @param paraMap
     * @Date 2018/3/2 14:18
     */
    List<BusIncrementFileInfo> listByZt(Map<String,Object> paraMap);

    /**
     * 根据文件名更新增量文件信息
     * @Author wanghaiyang
     * @param  busIncrementFileInfo
     * @Date 2018/3/2 14:24
     */
    boolean updateByFile(BusIncrementFileInfo busIncrementFileInfo);

    /**
     * 获取待上传的增量文件列表
     * @author konggang
     * @date 2018/3/21 11:34
     */
    List<String> getIncrFilePutList();
}
