package com.xhw.logcollection.job.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xhw.logcollection.job.entity.BusWarnDefine;
import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.job.mapper.BusWarnDefineMapper;
import com.xhw.logcollection.model.api.ApiResultModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author 孔纲
 * @Date 2018/5/2
 */
@Api("异常定义")
@RestController
@RequestMapping("api/warndefine")
public class BusWarnDefineController {

    @Autowired
    private BusWarnDefineMapper busWarnDefineMapper;

    private Gson gson = new GsonBuilder().serializeNulls().create();

    @ApiOperation(value = "返回错误CODE列表", httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/codeList", method = RequestMethod.POST, produces = "*/*")
    public String codeList(){
        ApiResultModel apiResult = new ApiResultModel();
        try{
            List<String> codes = busWarnDefineMapper.codeList();
            apiResult.setData(codes);
            apiResult.setStatus("1");
            apiResult.setReason("获取数据成功");
        }catch (Exception e){
            e.printStackTrace();
            apiResult.setStatus("-1");
            apiResult.setReason("系统异常，请联系管理员");
        }
        return gson.toJson(apiResult);
    }

    @ApiOperation(value = "根据错误CODE查询错误信息", httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/getInfoByCode", method = RequestMethod.POST, produces = "*/*")
    public String getInfoByCode(@ApiParam(value = "Json字符串") @RequestBody String data){
        ApiResultModel apiResult = new ApiResultModel();
        try{
            Map paraMap = gson.fromJson(data, HashMap.class);
            String code = (String) paraMap.get("code");
            BusWarnDefine warnDefine = busWarnDefineMapper.selectByPrimaryKey(code);
            apiResult.setData(warnDefine);
            apiResult.setStatus("1");
            apiResult.setReason("获取数据成功");
        }catch (Exception e){
            e.printStackTrace();
            apiResult.setStatus("-1");
            apiResult.setReason("系统异常，请联系管理员");
        }
        return gson.toJson(apiResult);
    }
}
