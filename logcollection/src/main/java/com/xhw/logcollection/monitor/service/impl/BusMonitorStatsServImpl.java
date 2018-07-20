package com.xhw.logcollection.monitor.service.impl;

import com.xhw.logcollection.monitor.entity.BusMonitorStats;
import com.xhw.logcollection.monitor.mapper.BusMonitorStatsMapper;
import com.xhw.logcollection.monitor.service.BusMonitorStatsServ;
import com.xhw.logcollection.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author 孔纲
 * @date 2018/3/7
 */
@Service
public class BusMonitorStatsServImpl implements BusMonitorStatsServ{

    @Autowired
    private BusMonitorStatsMapper mapper;

    public Map<String, Object> queryOverallReportDatas(String date){
        List<BusMonitorStats> datas = mapper.queryOverallDatasByDate(date);
        // 拼装报表数据
        Map<String, Object> result = new HashMap<>();
        List<Object> jgxtlb = new ArrayList<>(); // 系统类别
        List<Object> clcjl = new ArrayList<>(); //存量采集量
        List<Object> cldcjl = new ArrayList<>(); //存量数据待采集量
        List<Object> insertl = new ArrayList<>(); //Insert采集量
        List<Object> updatel = new ArrayList<>(); //Update采集量
        List<Object> deletel = new ArrayList<>(); //Delete采集量
        List<Object> clwjscs = new ArrayList<>(); //存量数据文件上传量
        List<Object> clwjdcs = new ArrayList<>(); //存量数据文件待传量
        List<Object> zlwjscs = new ArrayList<>(); //增量数据文件上传量
        List<Object> zlwjdcs = new ArrayList<>(); //增量数据文件待传量

        for(BusMonitorStats data:datas){
            jgxtlb.add(data.getJgxtlb());
            clcjl.add(data.getCLCJL());
            cldcjl.add(data.getCLDCJL());
            insertl.add(data.getINSERTL());
            updatel.add(data.getUPDATEL());
            deletel.add(data.getDELETEL());
            clwjscs.add(data.getCLWJSCS());
            clwjdcs.add(data.getCLWJDCS());
            zlwjscs.add(data.getZLWJSCS());
            zlwjdcs.add(data.getZLWJDCS());
        }

        result.put("jgxtlb",jgxtlb);
        result.put("clcjl",clcjl);
        result.put("cldcjl",cldcjl);
        result.put("insertl",insertl);
        result.put("updatel",updatel);
        result.put("deletel",deletel);
        result.put("clwjscs",clwjscs);
        result.put("clwjdcs",clwjdcs);
        result.put("zlwjscs",zlwjscs);
        result.put("zlwjdcs",zlwjdcs);

        return result;
    }

    @Override
    public Map<String, Object> queryTrendReportDatas(String xtlb, String startDate, String endDate) {
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("xtlb", xtlb);
        List<BusMonitorStats> datas = mapper.queryTrendDatas(params);
        // 拼装报表数据
        Map<String, Object> result = new HashMap<>();
        List<Object> tjrq = new ArrayList<>(); // 时间数组
        List<Object> clcjl = new ArrayList<>(); //存量采集量
        List<Object> cldcjl = new ArrayList<>(); //存量数据待采集量
        List<Object> insertl = new ArrayList<>(); //Insert采集量
        List<Object> updatel = new ArrayList<>(); //Update采集量
        List<Object> deletel = new ArrayList<>(); //Delete采集量
        List<Object> clwjscs = new ArrayList<>(); //存量数据文件上传量
        List<Object> clwjdcs = new ArrayList<>(); //存量数据文件待传量
        List<Object> zlwjscs = new ArrayList<>(); //增量数据文件上传量
        List<Object> zlwjdcs = new ArrayList<>(); //增量数据文件待传量

        for(BusMonitorStats data:datas){
            Date date = data.getTjrq();
            tjrq.add(DateUtil.date2Str(date, "yyyy-MM-dd"));
            clcjl.add(data.getCLCJL());
            cldcjl.add(data.getCLDCJL());
            insertl.add(data.getINSERTL());
            updatel.add(data.getUPDATEL());
            deletel.add(data.getDELETEL());
            clwjscs.add(data.getCLWJSCS());
            clwjdcs.add(data.getCLWJDCS());
            zlwjscs.add(data.getZLWJSCS());
            zlwjdcs.add(data.getZLWJDCS());
        }

        result.put("tjrq",tjrq);
        result.put("clcjl",clcjl);
        result.put("cldcjl",cldcjl);
        result.put("insertl",insertl);
        result.put("updatel",updatel);
        result.put("deletel",deletel);
        result.put("clwjscs",clwjscs);
        result.put("clwjdcs",clwjdcs);
        result.put("zlwjscs",zlwjscs);
        result.put("zlwjdcs",zlwjdcs);

        return result;
    }
}
