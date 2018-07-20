package com.xhw.logcollection.monitor.controller;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xhw.logcollection.model.api.ApiResultModel;
import com.xhw.logcollection.model.dto.BusStockBreakFileDto;
import com.xhw.logcollection.monitor.entity.BusStockBreakPointInfo;
import com.xhw.logcollection.monitor.service.BusStockBreakPointInfoServ;
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
 * 存量断点态控制层
 * @version 0.0.1
 * @Author wanghaiyang
 * @Description
 * @Date 2018/2/28 17:27
 * @Modified By:
 */
@Api("存量数据断点")
@RestController
@RequestMapping("api/stockbreak")
public class BusStockBreakPointInfoController {

    private Gson gson = new GsonBuilder().serializeNulls().create();

    @Autowired
    private BusStockBreakPointInfoServ breakPointInfoServ;

    @ApiOperation(value = "根据条件获取存量数据断点文件列表", httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "*/*")
    public String list(@ApiParam(value = "Json字符串") @RequestBody String data){
        ApiResultModel apiResult = new ApiResultModel();
        try{
            Map paraMap = gson.fromJson(data,HashMap.class);
            PageInfo<BusStockBreakFileDto> pageInfo = breakPointInfoServ.listBreakFileByCondition(paraMap);
            apiResult.setPageInfo(pageInfo);
        }catch (Exception e){
            e.printStackTrace();
            apiResult.setStatus("-1");
            apiResult.setReason("系统异常，请联系管理员");
        }
        return gson.toJson(apiResult);
    }

    @ApiOperation(value = "根据条件获取存量数据断点列表", httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/listBreakpoint", method = RequestMethod.POST, produces = "*/*")
    public String listBreakpoint(@ApiParam(value = "Json字符串") @RequestBody String data){
        ApiResultModel apiResult = new ApiResultModel();
        try{
            Map paraMap = gson.fromJson(data, HashMap.class);
            PageInfo<BusStockBreakPointInfo> pageInfo = breakPointInfoServ.listByCondition(paraMap);
            apiResult.setPageInfo(pageInfo);
        }catch (Exception e){
            e.printStackTrace();
            apiResult.setStatus("-1");
            apiResult.setReason("系统异常，请联系管理员");
        }
        return gson.toJson(apiResult);
    }


}
