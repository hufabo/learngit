package com.xhw.logcollection.job.controller;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xhw.logcollection.job.entity.ContextBean;
import com.xhw.logcollection.job.entity.MonitorTask;
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
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 监控任务controller
 *
 * @author 孔纲
 * @date 2018/3/5
 */
@Api("监控任务")
@RestController
@RequestMapping("api/monitortask")
public class MonitorTaskController {

    private Gson gson = new GsonBuilder().serializeNulls().create();

    @Autowired
    private MonitorTaskServ monitorTaskServ;

    @Autowired
    private MonitorTaskRunlogServ taskRunlogServ;

    @ApiOperation(value = "返回错误CODE下拉数据", httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/cwcodeListData", method = RequestMethod.POST, produces = "*/*")
    @Deprecated
    public String cwcodeListData(){
        ApiResultModel apiResult = new ApiResultModel();
        try{
            List<Map<String, String>> cwcodes = new ArrayList<>();
//            for (ContextBean.CWCODE e : ContextBean.CWCODE.values()) {
//                Map<String, String> cwcode = new HashMap<>();
//                cwcode.put("code",e.getCode());
//                cwcode.put("name",e.getMsg());
//                cwcodes.add(cwcode);
//            }
            apiResult.setData(cwcodes);
            apiResult.setStatus("1");
            apiResult.setReason("获取数据成功");
        }catch (Exception e){
            e.printStackTrace();
            apiResult.setStatus("-1");
            apiResult.setReason("系统异常，请联系管理员");
        }
        return gson.toJson(apiResult);
    }

    @ApiOperation(value = "返回系统类别下拉数据", httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/xtlbListData", method = RequestMethod.POST, produces = "*/*")
    public String xtlbListData(){
        ApiResultModel apiResult = new ApiResultModel();
        try{
            List<Map<String, String>> xtlbs = ContextBean.getBusCfgGlobalBean().getXtlb();
            apiResult.setData(xtlbs);
            apiResult.setStatus("1");
            apiResult.setReason("获取数据成功");
        }catch (Exception e){
            e.printStackTrace();
            apiResult.setStatus("-1");
            apiResult.setReason("系统异常，请联系管理员");
        }
        return gson.toJson(apiResult);
    }

    @ApiOperation(value = "任务触发时间刷新")
    @ResponseBody
    @RequestMapping(value = "/refreshJob",method = RequestMethod.GET)
    public Map<String, Object> refreshJob(@ApiParam(value = "任务ID") @RequestParam String taskId,
                                        @ApiParam(value = "cron表达式") @RequestParam String cronExpression){
        Map<String, Object> result = new HashMap<>();
        try {
            monitorTaskServ.refreshJobTriggerByTaskId(taskId, cronExpression);
            result.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
        }
        return result;
    }

    @ApiOperation(value = "任务挂起")
    @ResponseBody
    @RequestMapping(value = "/pauseJob",method = RequestMethod.GET)
    public Map<String, Object> pauseJob(@ApiParam(value = "任务ID") @RequestParam String taskId){
        Map<String, Object> result = new HashMap<>();
        try {
            monitorTaskServ.pauseJobByTaskId(taskId);
            result.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
        }
        return result;
    }

    @ApiOperation(value = "任务恢复")
    @ResponseBody
    @RequestMapping(value = "/resumeJob", method = RequestMethod.GET)
    public Map<String, Object> resumeJob(@ApiParam(value = "任务ID") @RequestParam String taskId){
        Map<String, Object> result = new HashMap<>();
        try {
            monitorTaskServ.resumeJobByTaskId(taskId);
            result.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
        }
        return result;
    }

    @ApiOperation(value = "配置文件刷新")
    @ResponseBody
    @RequestMapping(value = "/refreshGlobalProperties", method = RequestMethod.GET)
    public Map<String, Object> refreshGlobalProperties(){
        Map<String, Object> result = new HashMap<>();
        try {
            LoadGlobalPropertiesUtil.refresh();
            result.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
        }
        return result;
    }

    @ApiOperation(value = "任务启动器")
    @ResponseBody
    @RequestMapping(value = "/launcher",method = RequestMethod.GET)
    public Map<String, Object> launcher(@ApiParam(value = "任务ID") @RequestParam String taskId){
        Map<String, Object> result = new HashMap<>();
        try {
            monitorTaskServ.startTaskByTaskId(taskId);
            result.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("error", e.getMessage());
        }
        return result;
    }

    @ApiOperation(value = "任务管理界面列表", httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/toList", method = RequestMethod.POST, produces = "*/*")
    public String toList(@ApiParam(value = "Json字符串") @RequestBody String data){
        ApiResultModel apiResult = new ApiResultModel();
        try{
            Map paraMap = gson.fromJson(data, HashMap.class);
            PageInfo<MonitorTask> pageInfo = monitorTaskServ.queryList(paraMap);
            apiResult.setPageInfo(pageInfo);
        }catch (Exception e){
            e.printStackTrace();
            apiResult.setStatus("-1");
            apiResult.setReason("系统异常，请联系管理员");
        }
        return gson.toJson(apiResult);
    }

    @ApiOperation(value = "根据条件监控任务列表", httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "*/*")
    public String list(@ApiParam(value = "Json字符串") @RequestBody String data){
        ApiResultModel apiResult = new ApiResultModel();
        try{
            Map paraMap = gson.fromJson(data, HashMap.class);
            PageInfo<MonitorTaskDto> pageInfo = monitorTaskServ.listByCondition(paraMap);
            apiResult.setPageInfo(pageInfo);
        }catch (Exception e){
            e.printStackTrace();
            apiResult.setStatus("-1");
            apiResult.setReason("系统异常，请联系管理员");
        }
        return gson.toJson(apiResult);
    }

    @ApiOperation(value = "任务详情展示接口", httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/detail", method = RequestMethod.POST, produces = "*/*")
    public String detail(@ApiParam(value = "任务编号") @RequestBody String data){
        ApiResultModel apiResult = new ApiResultModel();
        try{
            Map<String, Object> datas = new HashMap<>();
            // 查询任务信息
            Map paraMap = gson.fromJson(data, HashMap.class);
            String rwbh = (String) paraMap.get("rwbh");
            MonitorTask task = new MonitorTask();
            task.setRwbh(rwbh);
            MonitorTask monitorTask = monitorTaskServ.selectOne(task);
            datas.put("task", monitorTask);
            // 查询任务运行日志列表
            List<MonitorWarnDto> warnList = taskRunlogServ.getTaskRunLogList(paraMap);
            // 将时间转换成字符串
            datas.put("logs", JSON.dataTrans(warnList));
            apiResult.setData(datas);
            apiResult.setStatus("1");
            apiResult.setReason("数据获取成功");
        }catch (Exception e){
            e.printStackTrace();
            apiResult.setStatus("-1");
            apiResult.setReason("系统异常，请联系管理员");
        }
        return gson.toJson(apiResult);
    }

    @ApiOperation(value = "任务出错率统计", httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/warnRate", method = RequestMethod.POST, produces = "*/*")
    public String warnRate(@ApiParam(value = "json字符串") @RequestBody String data){
        ApiResultModel apiResult = new ApiResultModel();
        try{
            Map<String, Object> datas = new HashMap<>();
            Map paraMap = gson.fromJson(data, HashMap.class);
            // 成功的任务数
            HashMap<String, Object> params = new HashMap<>();
            params.put("startDate",paraMap.get("date"));
            params.put("endDate", paraMap.get("date"));
            Long allTask = monitorTaskServ.getTaskCountByCondition(params);
            datas.put("total", allTask);
            // 失败的任务数
            params.put("yxjg",2);
            Long failTask = monitorTaskServ.getTaskCountByCondition(params);
            datas.put("warn", failTask);
            // 失败率
            String warnRate = "0.00%";
            if(allTask != 0){
                DecimalFormat decimalFormat = new DecimalFormat("0.00%");
                warnRate = decimalFormat.format(failTask.floatValue() / allTask.floatValue());
            }
            datas.put("warnRate", warnRate);
            apiResult.setData(datas);
            apiResult.setStatus("1");
            apiResult.setReason("数据获取成功");
        }catch (Exception e){
            e.printStackTrace();
            apiResult.setStatus("-1");
            apiResult.setReason("系统异常，请联系管理员");
        }
        return gson.toJson(apiResult);
    }

}

