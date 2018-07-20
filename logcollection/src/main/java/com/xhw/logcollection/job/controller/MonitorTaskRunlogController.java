package com.xhw.logcollection.job.controller;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.job.entity.MonitorTask;
import com.xhw.logcollection.job.entity.MonitorTaskRunlog;
import com.xhw.logcollection.job.service.MonitorTaskRunlogServ;
import com.xhw.logcollection.job.service.MonitorTaskServ;
import com.xhw.logcollection.model.api.ApiResultModel;
import com.xhw.logcollection.model.dto.MonitorTaskDto;
import com.xhw.logcollection.model.dto.MonitorWarnDto;
import com.xhw.logcollection.util.JSON;
import com.xhw.logcollection.util.LoadGlobalPropertiesUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 孔纲
 * @date 2018/3/5
 */
@Api("任务运行情况")
@RestController
@RequestMapping("api/taskrunlog")
public class MonitorTaskRunlogController {

    private Gson gson = new GsonBuilder().serializeNulls().create();

    @Autowired
    private MonitorTaskRunlogServ taskRunlogServ;

    @ApiOperation(value = "任务运行记录列表", httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "*/*")
    public String list(@ApiParam(value = "Json字符串") @RequestBody String data){
        ApiResultModel apiResult = new ApiResultModel();
        try{
            Map paraMap = gson.fromJson(data, HashMap.class);
            PageInfo<MonitorTaskRunlog> pageInfo = taskRunlogServ.listByCondition(paraMap);
            apiResult.setPageInfo(pageInfo);
        }catch (Exception e){
            e.printStackTrace();
            apiResult.setStatus("-1");
            apiResult.setReason("系统异常，请联系管理员");
        }
        return gson.toJson(apiResult);
    }

}

