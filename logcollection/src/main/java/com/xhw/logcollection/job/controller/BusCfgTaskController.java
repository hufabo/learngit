package com.xhw.logcollection.job.controller;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xhw.logcollection.job.entity.BusCfgTask;
import com.xhw.logcollection.job.service.BusCfgTaskServ;
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
import java.util.List;
import java.util.Map;

/**
 * 单表日志采集参数控制层
 * @version 0.0.1
 * @Author wanghaiyang
 * @Description
 * @Date 2018/2/28 14:58
 * @Modified By:
 */
@Api("单表日志采集参数")
@RestController
@RequestMapping("api/taskcfg")
public class BusCfgTaskController {

    private Gson gson = new GsonBuilder().serializeNulls().create();

    @Autowired
    private BusCfgTaskServ cfgTaskServ;

    @ApiOperation(value = "根据条件获取单表日志采集参数列表", httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "*/*")
    public String list(@ApiParam(value = "Json字符串") @RequestBody String data){
        ApiResultModel apiResult = new ApiResultModel();
        try{
            Map paraMap = gson.fromJson(data,HashMap.class);
            PageInfo<BusCfgTask> pageInfo = cfgTaskServ.listByCondition(paraMap);
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

    @ApiOperation(value = "获取所有系统类别代码", httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/listxtlb", method = RequestMethod.POST, produces = "*/*")
    public String listxtlb(){
        ApiResultModel apiResult = new ApiResultModel();
        try{
            List<String> xtlbList = cfgTaskServ.listJgxtlb();
            if(xtlbList!=null && xtlbList.size()>0){
                apiResult.setStatus("1");
                apiResult.setReason("数据获取成功");

                apiResult.setData(xtlbList);
            }else if(xtlbList.size()==0) {
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
