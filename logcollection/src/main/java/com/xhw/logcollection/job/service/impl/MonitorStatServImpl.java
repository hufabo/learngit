package com.xhw.logcollection.job.service.impl;

import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.job.service.MonitorStatServ;
import com.xhw.logcollection.monitor.entity.BusMonitorStats;
import com.xhw.logcollection.monitor.mapper.BusMonitorStatsMapper;
import com.xhw.logcollection.monitor.mapper.BusStockDealStatusMapper;
import com.xhw.logcollection.util.DateUtil;
import com.xhw.logcollection.util.LoadGlobalPropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author 孔纲
 * @Date 2018/3/28
 */
@Service
public class MonitorStatServImpl implements MonitorStatServ {

    @Autowired
    private BusMonitorStatsMapper monitorStatsMapper;

    @Autowired
    private BusStockDealStatusMapper dealStatusMapper;

    @Override
    public void queryAndSaveMonitorStat(String xtlb) {
        Map<String, Object> params = new HashMap<>();
        params.put("jgxtlb",xtlb);
        params.put("tjrq", DateUtil.dateNow2str("yyyyMMdd"));
        BusMonitorStats monitorStats = monitorStatsMapper.queryStatDatas(params);
        if(monitorStats == null){
            monitorStats =  new BusMonitorStats();
        }
        String babh = LoadGlobalPropertiesUtil.getProperty("system.babh");
        monitorStats.setBabh(babh);
        monitorStats.setJgxtlb(xtlb);
        monitorStats.setTjrq(new Date());
        monitorStats.setGXSJ(new Date());
        monitorStatsMapper.insert(monitorStats);
    }

}
