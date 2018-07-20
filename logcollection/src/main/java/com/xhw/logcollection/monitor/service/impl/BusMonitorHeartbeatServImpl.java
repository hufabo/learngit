package com.xhw.logcollection.monitor.service.impl;

import com.xhw.logcollection.exception.ParameterException;
import com.xhw.logcollection.monitor.entity.BusMonitorHeartbeat;
import com.xhw.logcollection.monitor.mapper.BusMonitorHeartbeatMapper;
import com.xhw.logcollection.monitor.service.BusMonitorHeartbeatServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 心跳状态上报服务接口实现类
 *
 * @author yangconghong
 * @version 0.0.1
 * @date 2018-02-28
 * @note
 * @update 修改日期      修改人    修改内容
 * -----------------------------------------------
 */
@Service
public class BusMonitorHeartbeatServImpl implements BusMonitorHeartbeatServ {

    @Autowired
    private BusMonitorHeartbeatMapper mapper;

    /**
     * 保存
     *
     * @param bean
     * @throws Exception
     */
    @Override
    public void save(BusMonitorHeartbeat bean) throws Exception {
        // 有则更新，无则新增，避免重新新增报错
        int i = mapper.updateByPrimaryKeySelective(bean);
        if(i == 0){
            mapper.insertSelective(bean);
        }
    }

    @Override
    public Map<String, Object> queryHeartbeatReportDatas(Map<String, Object> params) {
        // d 分天统计 h 分时统计
        String type = (String) params.get("type");
        if("day".equals(type)){
            params.put("pattern","yyyy-mm-dd");
        }else if("hour".equals(type)){
            params.put("pattern","yyyy-mm-dd hh24");
        }else{
            throw new ParameterException("无效的type参数！");
        }
        List<Map<String, Object>> busMonitorHeartbeats = mapper.queryHeartbeatReportDatas(params);
        // 拼装报表数据
        Map<String, Object> result = new HashMap<>();
        List<Object> timeList = new ArrayList<>();
        List<Object> cpuList = new ArrayList<>();
        List<Object> ramList = new ArrayList<>();
        List<Object> diskList = new ArrayList<>();
        List<Object> top5List = new ArrayList<>();
        for(Map<String, Object> data:busMonitorHeartbeats){
            timeList.add(data.get("TIME"));
            cpuList.add(data.get("CPUSYL"));
            ramList.add(data.get("NCSYL"));
            diskList.add(data.get("CPSYL"));
            top5List.add(data.get("XTFZ"));
        }
        result.put("time", timeList);
        result.put("cpu", cpuList);
        result.put("ram", ramList);
        result.put("disk", diskList);
        result.put("top5", top5List);
        return result;
    }

}
