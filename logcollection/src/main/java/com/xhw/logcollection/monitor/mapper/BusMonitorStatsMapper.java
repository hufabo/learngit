package com.xhw.logcollection.monitor.mapper;

import com.xhw.logcollection.monitor.entity.BusMonitorStats;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author 孔纲
 * @date 2018/3/7
 */
public interface BusMonitorStatsMapper extends Mapper<BusMonitorStats> {

    /**
     * 根据日期查询整体统计报表需要的数据
     * @author konggang
     * @date 2018/3/7 14:32
     */
    List<BusMonitorStats> queryOverallDatasByDate(String date);

    /**
     * 根据条件查询趋势统计报表需要的数据
     * @author konggang
     * @date 2018/3/7 15:34
     */
    List<BusMonitorStats> queryTrendDatas(Map<String, Object> params);

    /**
     * 查询采集情况数据
     * @author konggang
     * @date 2018/3/29 13:55
     */
    BusMonitorStats queryStatDatas(Map<String, Object> params);
}
