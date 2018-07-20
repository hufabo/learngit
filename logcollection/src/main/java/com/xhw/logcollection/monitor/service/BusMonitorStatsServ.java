package com.xhw.logcollection.monitor.service;

import java.util.Map;

/**
 *
 * @author 孔纲
 * @date 2018/3/7
 */
public interface BusMonitorStatsServ {

    /**
     * 查询整体情况统计报表数据（柱状图）
     * @author konggang
     * @date 2018/3/7 13:41
     */
    Map<String, Object> queryOverallReportDatas(String date);

    /**
     * 查询趋势统计报表数据（折线图）
     * @author konggang
     * @date 2018/3/7 14:55
     */
    Map<String, Object> queryTrendReportDatas(String xtlb, String startDate, String endDate);
}
