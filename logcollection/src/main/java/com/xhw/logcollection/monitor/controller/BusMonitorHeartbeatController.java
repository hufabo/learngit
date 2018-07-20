package com.xhw.logcollection.monitor.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xhw.logcollection.exception.ParameterException;
import com.xhw.logcollection.model.api.ApiResultModel;
import com.xhw.logcollection.monitor.service.BusMonitorHeartbeatServ;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api("心跳监控")
@RestController
@RequestMapping("api/heartbeat")
public class BusMonitorHeartbeatController {

    private Gson gson = new GsonBuilder().serializeNulls().create();

    @Autowired
    private BusMonitorHeartbeatServ heartbeatServ;

    @ApiOperation(value = "根据开始结束时间获取心跳数据", httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/queryHeartbeatReportDatas", method = RequestMethod.POST, produces = "*/*")
    public String queryHeartbeatReportDatas(@ApiParam(value = "Json字符串") @RequestBody String data){
        ApiResultModel apiResult = new ApiResultModel();
        try{
            Map paraMap = gson.fromJson(data, HashMap.class);
            Map<String, Object> result = heartbeatServ.queryHeartbeatReportDatas(paraMap);
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
