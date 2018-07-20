package com.xhw.logcollection.monitor.mapper;

import com.xhw.logcollection.monitor.entity.BusMonitorRunlog;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 运行状态监控Mapper
 * @author 孔纲
 * @date 2018/3/2
 */
public interface BusMonitorRunlogMapper extends Mapper<BusMonitorRunlog> {

    /**
     * 根据条件返回列表
     * @author konggang
     * @date 2018/3/2 14:45
     */
    List<BusMonitorRunlog> listByCondition(Map<String,Object> paraMap);

    /**
     * 获取待同步数据列表
     * @Author wanghaiyang
     * @param  paraMap
     * @Date 2018/3/9 11:24
     */
    List<BusMonitorRunlog> listSync(Map<String,Object> paraMap);

    /**
     * 查询软件运行错误记录
     * @author konggang
     * @date 2018/3/21 15:51
     */
    List<BusMonitorRunlog> queryErrorLogs(Map<String,Object> paraMap);

}
