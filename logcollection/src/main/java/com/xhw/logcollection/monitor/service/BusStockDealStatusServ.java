package com.xhw.logcollection.monitor.service;

import com.github.pagehelper.PageInfo;
import com.xhw.logcollection.monitor.entity.BusStockDealStatus;

import java.util.List;
import java.util.Map;

/**
 * 存量数据处理状态服务层接口
 * @version 0.0.1
 * @Author wanghaiyang
 * @Description
 * @Date 2018/2/28 17:16
 * @Modified By:
 */
public interface BusStockDealStatusServ {

    /**
     * 根据条件获取存量数据处理列表
     * @Author:wanghaiyang
     * @Date: 2018/2/28 17:10
     * @params  paraMap
     * @Modified by:
     */
    PageInfo<BusStockDealStatus> listByCondition(Map<String,Object> paraMap);

    void updateCJWJS(Map<String,Object> paraMap);

    void updateRKWJS();
}
