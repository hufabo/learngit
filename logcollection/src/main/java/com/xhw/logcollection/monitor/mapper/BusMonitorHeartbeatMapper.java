package com.xhw.logcollection.monitor.mapper;

import com.xhw.logcollection.monitor.entity.BusMonitorHeartbeat;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface BusMonitorHeartbeatMapper extends Mapper<BusMonitorHeartbeat> {

   /**
    * 根据条件返回心跳列表
    * @author konggang
    * @date 2018/3/2 14:45
    */
    List<Map<String, Object>> queryHeartbeatReportDatas(Map<String,Object> paraMap);

    /**
     *
     * @Author wanghaiyang
     * @param  paraMap
     * @Date 2018/3/9 11:17
     */
    List<BusMonitorHeartbeat> listSync(Map<String,Object> paraMap);
}