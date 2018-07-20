package com.xhw.logcollection.monitor.service;

import com.github.pagehelper.PageInfo;
import com.xhw.logcollection.monitor.entity.BusIncrementBreakPointInfo;

import java.util.List;
import java.util.Map;

/**
 * @version 0.0.1
 * @Author wanghaiyang
 * @Description
 * @Date 2018/2/28 17:16
 * @Modified By:
 */
public interface BusIncrementBreakPointInfoServ {

    /**
     * 根据条件获取增量数据断点列表
     * @param paraMap
     * @return
     */
    PageInfo<BusIncrementBreakPointInfo> listBycondition(Map<String,Object> paraMap);
}
