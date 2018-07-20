package com.xhw.logcollection.job.controller;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xhw.logcollection.job.entity.BusCfgGlobal;
import com.xhw.logcollection.job.service.BusCfgGlobalServ;
import com.xhw.logcollection.model.api.ApiResultModel;
import com.xhw.logcollection.util.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 日志采集全局参数控制层
 * @version 0.0.1
 * @Author wanghaiyang
 * @Description
 * @Date 2018/2/28 14:58
 * @Modified By:
 */
@Api("日志采集全局参数")
@RestController
@RequestMapping("api/globalcfg")
public class BusCfgGlobalController {

    private Gson gson = new GsonBuilder().serializeNulls().create();

    @Autowired
    private BusCfgGlobalServ globalServ;

    @ApiOperation(value = "根据条件日志采集全局参数列表", httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "*/*")
    public String list(@ApiParam(value = "Json字符串") @RequestBody String data){
        ApiResultModel apiResult = new ApiResultModel();
        try{
            Map paraMap = gson.fromJson(data,HashMap.class);
            PageInfo<BusCfgGlobal> pageInfo = globalServ.listByCondition(paraMap);
            apiResult.setPageInfo(pageInfo);
        }catch (Exception e){
            e.printStackTrace();
            apiResult.setStatus("-1");
            apiResult.setReason("系统异常，请联系管理员");
        }
        return gson.toJson(apiResult);
    }
}
