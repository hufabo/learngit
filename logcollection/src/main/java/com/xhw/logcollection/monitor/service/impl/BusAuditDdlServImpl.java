package com.xhw.logcollection.monitor.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xhw.logcollection.monitor.entity.BusAuditDdl;
import com.xhw.logcollection.monitor.entity.BusIncrementBreakPointInfo;
import com.xhw.logcollection.monitor.mapper.BusAuditDdlMapper;
import com.xhw.logcollection.monitor.service.BusAuditDdlServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @version 0.0.1
 * @Author wanghaiyang
 * @Description
 * @Date 2018/2/28 17:16
 * @Modified By:
 */
@Service
public class BusAuditDdlServImpl implements BusAuditDdlServ {

    @Autowired
    private BusAuditDdlMapper auditDdlMapper;

    /**
     * 根据条件获取数据审计列表
     *
     * @param paraMap
     * @Author wanghaiyang
     * @Date 2018/3/1 16:35
     */
    @Override
    public PageInfo<BusAuditDdl> listByCondition(Map<String, Object> paraMap) {
        if(paraMap.get("curentPage")!=null && !paraMap.get("curentPage").toString().equals("")){
            PageHelper.startPage(Integer.valueOf(paraMap.get("curentPage").toString()),Integer.valueOf(paraMap.get("pageSize").toString()));
        }
        List<BusAuditDdl> auditDdlList = auditDdlMapper.listByCondition(paraMap);
        PageInfo<BusAuditDdl> pageInfo = new PageInfo<BusAuditDdl>(auditDdlList);
        return pageInfo;
    }
}
