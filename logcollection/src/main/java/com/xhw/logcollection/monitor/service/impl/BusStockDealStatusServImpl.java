package com.xhw.logcollection.monitor.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xhw.logcollection.job.entity.BusCfgTask;
import com.xhw.logcollection.job.mapper.BusCfgTaskMapper;
import com.xhw.logcollection.monitor.entity.BusStockDealStatus;
import com.xhw.logcollection.monitor.mapper.BusStockDealStatusMapper;
import com.xhw.logcollection.monitor.service.BusStockDealStatusServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 存量数据处理状态服务实现
 * @version 0.0.1
 * @Author wanghaiyang
 * @Description
 * @Date 2018/2/28 17:21
 * @Modified By:
 */
@Service
public class BusStockDealStatusServImpl implements BusStockDealStatusServ {

    @Autowired
    private BusStockDealStatusMapper stockDealStatusMapper;

    @Autowired
    private BusCfgTaskMapper cfgTaskMapper;

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
    public PageInfo<BusStockDealStatus> listByCondition(Map<String, Object> paraMap) {
        if(paraMap.get("curentPage")!=null && !paraMap.get("curentPage").toString().equals("")){
            PageHelper.startPage(Integer.valueOf(paraMap.get("curentPage").toString()),Integer.valueOf(paraMap.get("pageSize").toString()));
        }
        List<BusStockDealStatus> stockDealStatusList = stockDealStatusMapper.listByCondition(paraMap);
        PageInfo<BusStockDealStatus> pageInfo = new PageInfo<BusStockDealStatus>(stockDealStatusList);
        return pageInfo;
    }

    @Override
    public void updateCJWJS(Map<String, Object> paraMap) {
        List<BusStockDealStatus> statuses = stockDealStatusMapper.queryStockDealStatus(paraMap);
        for(BusStockDealStatus status:statuses){
            status.setGxsj(new Date());
            status.setZjqdsj(new Date());
            stockDealStatusMapper.updateByPrimaryKeySelective(status);

            status = stockDealStatusMapper.selectByPrimaryKey(status);
            BigDecimal currentTotal = status.getCjsjzl();
            BigDecimal total = status.getSjzl();
            // 如果采集完成，更新 存量数据处理状态表
            if(currentTotal.longValue() >= total.longValue()){
                status.setCjzt("2"); // 0-未启动， 1-正在执行，2-已完成
                status.setCjwcsj(new Date());
                stockDealStatusMapper.updateByPrimaryKeySelective(status);
                BusCfgTask cfgTask = new BusCfgTask();
                cfgTask.setJgxtlb(status.getJgxtlb());
                cfgTask.setBm(status.getBm());
                cfgTask.setClwcbj("2"); // 0-未启动，1-正在执行，2-已完成
                cfgTaskMapper.updateByPrimaryKeySelective(cfgTask);
            }
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void updateRKWJS() {
        List<BusStockDealStatus> statuses = stockDealStatusMapper.queryRKWJS();
        for(BusStockDealStatus status:statuses){
            if(status.getRkwjs() != null &&  status.getCjwjs()!= null){
                if(status.getRkwjs().longValue() == status.getCjwjs().longValue()){
                    //入库状态，0-未完成，1-已完成
                    status.setRkzt("1");
                    status.setRkwcsj(new Date());
                }
            }
            status.setGxsj(new Date());
            stockDealStatusMapper.updateByPrimaryKeySelective(status);
        }
    }
}
