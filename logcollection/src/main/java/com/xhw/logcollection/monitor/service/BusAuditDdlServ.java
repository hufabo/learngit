package com.xhw.logcollection.monitor.service;

import com.github.pagehelper.PageInfo;
import com.xhw.logcollection.monitor.entity.BusAuditDdl;

import java.util.List;
import java.util.Map;

/**
 * @version 0.0.1
 * @Author wanghaiyang
 * @Description
 * @Date 2018/2/28 17:16
 * @Modified By:
 */
public interface BusAuditDdlServ {

    /**
     * 根据条件获取数据审计列表
     * @Author wanghaiyang
     * @param paraMap
     * @Date 2018/3/1 16:35
     */
    PageInfo<BusAuditDdl> listByCondition(Map<String,Object> paraMap);
}
