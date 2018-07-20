package com.xhw.logcollection.monitor.controller;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xhw.logcollection.model.api.ApiResultModel;
import com.xhw.logcollection.monitor.entity.BusMonitorRunlog;
import com.xhw.logcollection.monitor.service.BusMonitorRunlogServ;
import com.xhw.logcollection.util.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api("运行监控")
@RestController
@RequestMapping("api/runlog")
public class BusMonitorRunlogController {

    private Gson gson = new GsonBuilder().serializeNulls().create();

    @Autowired
    private BusMonitorRunlogServ runlogServ;

    @ApiOperation(value = "获取错误运行日志列表", httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/errorLogs", method = RequestMethod.POST, produces = "*/*")
    public String errorLogs(@ApiParam(value = "Json字符串") @RequestBody String data){
        ApiResultModel apiResult = new ApiResultModel();
        try{
            Map paraMap = gson.fromJson(data, HashMap.class);
            List list = runlogServ.queryErrorLogs(paraMap);
            JSONArray dataArr = JSON.dataTrans(list);
            apiResult.setData(dataArr);
            apiResult.setStatus("1");
            apiResult.setReason("数据获取成功");
        }catch (Exception e){
            e.printStackTrace();
            apiResult.setStatus("-1");
            apiResult.setReason("系统异常，请联系管理员");
        }
        return gson.toJson(apiResult);
    }

    @ApiOperation(value = "根据条件获取运行监控列表", httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "*/*")
    public String list(@ApiParam(value = "Json字符串") @RequestBody String data){
        ApiResultModel apiResult = new ApiResultModel();
        try{
            Map paraMap = gson.fromJson(data, HashMap.class);
            PageInfo<BusMonitorRunlog> pageInfo = runlogServ.listByCondition(paraMap);
            if(pageInfo!=null &&pageInfo.getList().size()>0){
                apiResult.setStatus("1");
                apiResult.setReason("数据获取成功");

                JSONObject tbObj = new JSONObject();
                JSONArray dataArr = JSON.dataTrans(pageInfo.getList());
                tbObj.put("total",pageInfo.getTotal());
                tbObj.put("rows",dataArr);
                apiResult.setData(tbObj);
            }else if(pageInfo.getList().size()==0) {
                apiResult.setStatus("1");
                apiResult.setReason("暂无数据");
            }else {
                apiResult.setStatus("0");
                apiResult.setReason("数据获取失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            apiResult.setStatus("-1");
            apiResult.setReason("系统异常，请联系管理员");
        }
        return gson.toJson(apiResult);
    }
}
