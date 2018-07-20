package com.xhw.logcollection.monitor.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xhw.logcollection.model.dto.BusStockBreakFileDto;
import com.xhw.logcollection.monitor.entity.BusStockBreakPointInfo;
import com.xhw.logcollection.monitor.mapper.BusStockBreakPointInfoMapper;
import com.xhw.logcollection.monitor.service.BusStockBreakPointInfoServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class BusStockBreakPointInfoServImpl implements BusStockBreakPointInfoServ {

    @Autowired
    private BusStockBreakPointInfoMapper breakPointInfoMapper;

    /**
     * 根据条件获取存量数据处理列表
     *
     * @param paraMap
     * @Author:wanghaiyang
     * @Date: 2018/2/28 17:10
     * @params paraMap
     * @Modified by:
     */
    @Override
    public PageInfo<BusStockBreakFileDto> listBreakFileByCondition(Map<String, Object> paraMap) {
        if(paraMap.get("curentPage")!=null && !paraMap.get("curentPage").toString().equals("")){
            PageHelper.startPage(Integer.valueOf(paraMap.get("curentPage").toString()),Integer.valueOf(paraMap.get("pageSize").toString()));
        }
        List<BusStockBreakFileDto> stockDealStatusList = breakPointInfoMapper.listBreakFileByCondition(paraMap);
        PageInfo<BusStockBreakFileDto> pageInfo = new PageInfo<BusStockBreakFileDto>(stockDealStatusList);
        return pageInfo;
    }

    @Override
    public PageInfo<BusStockBreakPointInfo> listByCondition(Map<String, Object> paraMap) {
        if(paraMap.get("curentPage")!=null && !paraMap.get("curentPage").toString().equals("")){
            PageHelper.startPage(Integer.valueOf(paraMap.get("curentPage").toString()),Integer.valueOf(paraMap.get("pageSize").toString()));
        }
        List<BusStockBreakPointInfo> stockDealStatusList = breakPointInfoMapper.listByCondition(paraMap);
        PageInfo<BusStockBreakPointInfo> pageInfo = new PageInfo<>(stockDealStatusList);
        return pageInfo;
    }
}
