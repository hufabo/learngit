package com.xhw.logcollection.job.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xhw.logcollection.job.service.MonitorWarnServ;
import com.xhw.logcollection.model.api.ApiResultModel;
import com.xhw.logcollection.model.dto.MonitorWarnDto;
import com.xhw.logcollection.util.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * 异常告警controller
 * @author 孔纲
 * @date 2018/3/5
 */
@Api("异常告警")
@RestController
@RequestMapping("api/monitorwarn")
public class MonitorWarnController {

    private Gson gson = new GsonBuilder().serializeNulls().create();

    @Autowired
    private MonitorWarnServ monitorWarnServ;

    @ApiOperation(value = "根据条件监控任务列表", httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "*/*")
    public String list(@ApiParam(value = "Json字符串") @RequestBody String data){
        ApiResultModel apiResult = new ApiResultModel();
        try{
            Map paraMap = gson.fromJson(data, HashMap.class);
            PageInfo<MonitorWarnDto> pageInfo = monitorWarnServ.listByCondition(paraMap);
            apiResult.setPageInfo(pageInfo);
        }catch (Exception e){
            e.printStackTrace();
            apiResult.setStatus("-1");
            apiResult.setReason("系统异常，请联系管理员");
        }
        return gson.toJson(apiResult);
    }

    @ApiOperation(value = "处理异常", httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/handle", method = RequestMethod.POST, produces = "*/*")
    public String handle(@ApiParam(value = "Json字符串") @RequestBody String data){
        ApiResultModel apiResult = new ApiResultModel();
        try{
            Type type = new TypeToken<Map<String, Long>>() {}.getType();
            Map<String, Long> paraMap = gson.fromJson(data, type);
            long runid = paraMap.get("runid");
            long warnid = paraMap.get("warnid");
            monitorWarnServ.handle(runid, warnid);
            apiResult.setStatus("1");
            apiResult.setReason("操作成功");
        }catch (Exception e){
            e.printStackTrace();
            apiResult.setStatus("-1");
            apiResult.setReason("系统异常，请联系管理员");
        }
        return gson.toJson(apiResult);
    }
}
