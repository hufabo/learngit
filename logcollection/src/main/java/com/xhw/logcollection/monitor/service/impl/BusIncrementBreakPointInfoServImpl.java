package com.xhw.logcollection.monitor.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xhw.logcollection.monitor.entity.BusIncrementBreakPointInfo;
import com.xhw.logcollection.monitor.entity.BusStockDealStatus;
import com.xhw.logcollection.monitor.mapper.BusIncrementBreakPointInfoMapper;
import com.xhw.logcollection.monitor.service.BusIncrementBreakPointInfoServ;
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
public class BusIncrementBreakPointInfoServImpl implements BusIncrementBreakPointInfoServ {

    @Autowired
    private BusIncrementBreakPointInfoMapper incrementBreakPointInfoMapper;

    /**
     * 根据条件获取增量数据断点列表
     *
     * @param paraMap
     * @return
     */
    @Override
    public PageInfo<BusIncrementBreakPointInfo> listBycondition(Map<String, Object> paraMap) {
        if(paraMap.get("curentPage")!=null && !paraMap.get("curentPage").toString().equals("")){
            PageHelper.startPage(Integer.valueOf(paraMap.get("curentPage").toString()),Integer.valueOf(paraMap.get("pageSize").toString()));
        }
        List<BusIncrementBreakPointInfo> incrementBreakPointInfoList = incrementBreakPointInfoMapper.listByCondition(paraMap);
        PageInfo<BusIncrementBreakPointInfo> pageInfo = new PageInfo<BusIncrementBreakPointInfo>(incrementBreakPointInfoList);
        return pageInfo;
    }
}
