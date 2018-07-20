package com.xhw.logcollection.monitor.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xhw.logcollection.exception.ParameterException;
import com.xhw.logcollection.model.api.ApiResultModel;
import com.xhw.logcollection.monitor.service.BusMonitorStatsServ;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 孔纲
 * @date 2018/3/7
 */
@Api("数据采集情况统计")
@RestController
@RequestMapping("api/monitorStats")
public class BusMonitorStatsController {
    private Gson gson = new GsonBuilder().serializeNulls().create();

    @Autowired
    private BusMonitorStatsServ busMonitorStatsServ;

    @ApiOperation(value = "整体统计分析数据查询", httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/overall", method = RequestMethod.POST, produces = "*/*")
    public String overall(@ApiParam(value = "统计日期") @RequestBody String data){
        ApiResultModel apiResult = new ApiResultModel();
        try{
            Map paraMap = gson.fromJson(data, HashMap.class);
            String date = (String) paraMap.get("date");
            Map<String, Object> result = busMonitorStatsServ.queryOverallReportDatas(date);
            apiResult.setData(result);
            apiResult.setStatus("1");
            apiResult.setReason("数据获取成功");
        }catch (ParameterException e){
            e.printStackTrace();
            apiResult.setStatus("-1");
            apiResult.setReason(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            apiResult.setStatus("-1");
            apiResult.setReason("系统异常，请联系管理员");
        }
        return gson.toJson(apiResult);
    }

    @ApiOperation(value = "趋势统计分析数据查询", httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/trend", method = RequestMethod.POST, produces = "*/*")
    public String trend(@ApiParam(value = "JSON字符串") @RequestBody String data){
        ApiResultModel apiResult = new ApiResultModel();
        try{
            Map paraMap = gson.fromJson(data, HashMap.class);
            String xtlb = (String) paraMap.get("xtlb");
            String startDate = (String) paraMap.get("startDate");
            String endDate = (String) paraMap.get("endDate");
            Map<String, Object> result = busMonitorStatsServ.queryTrendReportDatas(xtlb, startDate, endDate);
            apiResult.setData(result);
            apiResult.setStatus("1");
            apiResult.setReason("数据获取成功");
        }catch (ParameterException e){
            e.printStackTrace();
            apiResult.setStatus("-1");
            apiResult.setReason(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            apiResult.setStatus("-1");
            apiResult.setReason("系统异常，请联系管理员");
        }
        return gson.toJson(apiResult);
    }
}
