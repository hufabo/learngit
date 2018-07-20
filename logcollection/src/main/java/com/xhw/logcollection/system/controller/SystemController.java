package com.xhw.logcollection.system.controller;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xhw.logcollection.model.api.ApiResultModel;
import com.xhw.logcollection.system.entity.Agent;
import com.xhw.logcollection.system.entity.DataSource;
import com.xhw.logcollection.system.entity.Register;
import com.xhw.logcollection.system.entity.ShareProtocol;
import com.xhw.logcollection.system.service.SystemConfigService;
import com.xhw.logcollection.util.ApiUtil;
import com.xhw.logcollection.util.Base64Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;


/**
 * @author rentie
 * @version 0.0.1
 * @date 2018/2/5
 * @note
 * @update 修改日期      修改人    修改内容
 * ----------------------------------------------
 */

@Api("系统配置")
@RestController
@RequestMapping("api/sysConfig")
public class SystemController {
	@Autowired
	private SystemConfigService systemConfigService;

	private Gson gson = new GsonBuilder().serializeNulls().create();

	@ApiOperation(value = "添加安管系统访问授权信息登记信息", httpMethod = "POST")
	@ResponseBody
	@RequestMapping(value = "/saveRegister", method = RequestMethod.POST, produces = "*/*")
	public String saveRegister(@ApiParam(value = "Json字符串") @RequestBody String data){
		ApiResultModel apiResult = new ApiResultModel();
		Register entity= null;
		try {
			entity= gson.fromJson(data,Register.class);
			if(entity.getAgxtip()==null ||entity.getAgxtip().equals("")){
				return gson.toJson(new ApiResultModel("0","","安管系统IP不能为空",""));
			}
			if(entity.getAgxtdk()==null || entity.getAgxtdk().toString().equals("")){
				return gson.toJson(new ApiResultModel("0","","端口不能为空",""));
			}
			if(entity.getBabh()==null || entity.getBabh().equals("")){
				return gson.toJson(new ApiResultModel("0","","备案编号不能为空",""));
			}
			if(entity.getJkfwsqm()==null ||entity.getJkfwsqm().equals("")){
				return gson.toJson(new ApiResultModel("0","","访问授权码不能为空",""));
			}
		} catch (JSONException jsonException){
			apiResult.setReason("json格式错误");
			apiResult.setStatus("-1");
			apiResult.setErrorMessage(jsonException.getMessage());
			return gson.toJson(apiResult);
		}
		try {
			int effectCount=systemConfigService.saveRegister(entity);
			if (effectCount>0){
				apiResult.setReason("添加成功");
				apiResult.setStatus("1");

			}else {
				apiResult.setReason("添加失败");
				apiResult.setStatus("0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			apiResult.setStatus("-1");
			apiResult.setErrorMessage(e.getMessage());
			apiResult.setReason("操作异常");
			return gson.toJson(apiResult);
		}
		return gson.toJson(apiResult);
	}

	@ApiOperation(value = "添加Agent方式采集配置信息", httpMethod = "POST")
	@ResponseBody
	@RequestMapping(value = "/saveAgent", method = RequestMethod.POST, produces = "*/*")
	public String saveAgent(@ApiParam(value = "Json字符串") @RequestBody String data){
		ApiResultModel apiResult = new ApiResultModel();
		Agent entity= null;
		try {
			entity= gson.fromJson(data,Agent.class);
		} catch (JSONException jsonException){
			apiResult.setReason("json格式错误");
			apiResult.setStatus("-1");
			apiResult.setErrorMessage(jsonException.getMessage());
			return gson.toJson(apiResult);
		}
		try {
			int effectCount=systemConfigService.saveAgent(entity);
			if (effectCount>0){
				apiResult.setReason("添加成功");
				apiResult.setStatus("1");

			}else {
				apiResult.setReason("添加失败");
				apiResult.setStatus("0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			apiResult.setStatus("-1");
			apiResult.setErrorMessage(e.getMessage());
			apiResult.setReason("操作异常");
			return gson.toJson(apiResult);
		}
		return gson.toJson(apiResult);
	}

	@ApiOperation(value = "添加目标数据库连接配置信息", httpMethod = "POST")
	@ResponseBody
	@RequestMapping(value = "/saveDataSource", method = RequestMethod.POST, produces = "*/*")
	public String saveDataSource(@ApiParam(value = "Json字符串") @RequestBody String data){
		ApiResultModel apiResult = new ApiResultModel();
		DataSource entity= null;
		try {
			entity= gson.fromJson(data,DataSource.class);
		} catch (JSONException jsonException){
			apiResult.setReason("json格式错误");
			apiResult.setStatus("-1");
			apiResult.setErrorMessage(jsonException.getMessage());
			return gson.toJson(apiResult);
		}
		try {
			int effectCount=systemConfigService.saveDataSource(entity);
			if (effectCount>0){
				apiResult.setReason("添加成功");
				apiResult.setStatus("1");

			}else {
				apiResult.setReason("添加失败");
				apiResult.setStatus("0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			apiResult.setStatus("-1");
			apiResult.setErrorMessage(e.getMessage());
			apiResult.setReason("操作异常");
			return gson.toJson(apiResult);
		}
		return gson.toJson(apiResult);
	}

	@ApiOperation(value = "添加SMB/NFS共享方式采集配置信息", httpMethod = "POST")
	@ResponseBody
	@RequestMapping(value = "/saveShareProtocol", method = RequestMethod.POST, produces = "*/*")
	public String saveShareProtocol(@ApiParam(value = "Json字符串") @RequestBody String data){
		ApiResultModel apiResult = new ApiResultModel();
		ShareProtocol entity= null;
		try {
			entity= gson.fromJson(data,ShareProtocol.class);
		} catch (JSONException jsonException){
			apiResult.setReason("json格式错误");
			apiResult.setStatus("-1");
			apiResult.setErrorMessage(jsonException.getMessage());
			return gson.toJson(apiResult);
		}
		try {
			int effectCount=systemConfigService.saveShareProtocol(entity);
			if (effectCount>0){
				apiResult.setReason("添加成功");
				apiResult.setStatus("1");

			}else {
				apiResult.setReason("添加失败");
				apiResult.setStatus("0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			apiResult.setStatus("-1");
			apiResult.setErrorMessage(e.getMessage());
			apiResult.setReason("操作异常");
			return gson.toJson(apiResult);
		}
		return gson.toJson(apiResult);
	}

	@ApiOperation(value = "修改安管系统访问授权信息登记信息", httpMethod = "POST")
	@ResponseBody
	@RequestMapping(value = "/updateRegister", method = RequestMethod.POST, produces = "*/*")
	public String updateRegister(@ApiParam(value = "Json字符串") @RequestBody String data){
		ApiResultModel apiResult = new ApiResultModel();
		Register entity= null;
		try {
			entity= gson.fromJson(data,Register.class);
		} catch (JSONException jsonException){
			apiResult.setReason("json格式错误");
			apiResult.setStatus("-1");
			apiResult.setErrorMessage(jsonException.getMessage());
			return gson.toJson(apiResult);
		}
		try {
			int effectCount=systemConfigService.updateRegister(entity);
			if (effectCount>0){
				apiResult.setReason("修改成功");
				apiResult.setStatus("1");

			}else {
				apiResult.setReason("修改失败");
				apiResult.setStatus("0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			apiResult.setStatus("-1");
			apiResult.setErrorMessage(e.getMessage());
			apiResult.setReason("操作异常");
			return gson.toJson(apiResult);
		}
		return gson.toJson(apiResult);
	}

	@ApiOperation(value = "修改Agent方式采集配置信息", httpMethod = "POST")
	@ResponseBody
	@RequestMapping(value = "/updateAgent", method = RequestMethod.POST, produces = "*/*")
	public String updateAgent(@ApiParam(value = "Json字符串") @RequestBody String data){
		ApiResultModel apiResult = new ApiResultModel();
		Agent entity= null;
		try {
			entity= gson.fromJson(data,Agent.class);
		} catch (JSONException jsonException){
			apiResult.setReason("json格式错误");
			apiResult.setStatus("-1");
			apiResult.setErrorMessage(jsonException.getMessage());
			return gson.toJson(apiResult);
		}
		try {
			int effectCount=systemConfigService.updateAgent(entity);
			if (effectCount>0){
				apiResult.setReason("修改成功");
				apiResult.setStatus("1");
			}else {
				apiResult.setReason("修改失败");
				apiResult.setStatus("0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			apiResult.setStatus("-1");
			apiResult.setErrorMessage(e.getMessage());
			apiResult.setReason("操作异常");
			return gson.toJson(apiResult);
		}
		return gson.toJson(apiResult);
	}

	@ApiOperation(value = "修改目标数据库连接配置信息", httpMethod = "POST")
	@ResponseBody
	@RequestMapping(value = "/updateDataSource", method = RequestMethod.POST, produces = "*/*")
	public String updateDataSource(@ApiParam(value = "Json字符串") @RequestBody String data){
		ApiResultModel apiResult = new ApiResultModel();
		DataSource entity= null;
		try {
			entity= gson.fromJson(data,DataSource.class);
		} catch (JSONException jsonException){
			apiResult.setReason("json格式错误");
			apiResult.setStatus("-1");
			apiResult.setErrorMessage(jsonException.getMessage());
			return gson.toJson(apiResult);
		}
		try {
			int effectCount=systemConfigService.updateDataSource(entity);
			if (effectCount>0){
				apiResult.setReason("修改成功");
				apiResult.setStatus("1");
			}else {
				apiResult.setReason("修改失败");
				apiResult.setStatus("0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			apiResult.setStatus("-1");
			apiResult.setErrorMessage(e.getMessage());
			apiResult.setReason("操作异常");
			return gson.toJson(apiResult);
		}
		return gson.toJson(apiResult);
	}

	@ApiOperation(value = "修改SMB/NFS共享方式采集配置信息", httpMethod = "POST")
	@ResponseBody
	@RequestMapping(value = "/updateShareProtocol", method = RequestMethod.POST, produces = "*/*")
	public String updateShareProtocol(@ApiParam(value = "Json字符串") @RequestBody String data){
		ApiResultModel apiResult = new ApiResultModel();
		ShareProtocol entity= null;
		try {
			entity= gson.fromJson(data,ShareProtocol.class);
		} catch (JSONException jsonException){
			apiResult.setReason("json格式错误");
			apiResult.setStatus("-1");
			apiResult.setErrorMessage(jsonException.getMessage());
			return gson.toJson(apiResult);
		}
		try {
			int effectCount=systemConfigService.updateShareProtocol(entity);
			if (effectCount>0){
				apiResult.setReason("修改成功");
				apiResult.setStatus("1");

			}else {
				apiResult.setReason("修改失败");
				apiResult.setStatus("0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			apiResult.setStatus("-1");
			apiResult.setErrorMessage(e.getMessage());
			apiResult.setReason("操作异常");
			return gson.toJson(apiResult);
		}
		return gson.toJson(apiResult);
	}

	@ApiOperation(value = "查询Agent方式采集配置信息列表", httpMethod = "POST")
	@ResponseBody
	@RequestMapping(value = "/listAgent", method = RequestMethod.POST, produces = "*/*")
	public String listAgent(@ApiParam(value = "Json字符串") @RequestBody String data){
		ApiResultModel apiResult = new ApiResultModel();
		String rstStr="";
		Map<String,Object> map= null;
		try {
			map= gson.fromJson(data,HashMap.class);
		}catch (JSONException jsonException){
			apiResult.setReason("json格式错误");
			apiResult.setStatus("-1");
			apiResult.setErrorMessage(jsonException.getMessage());
			return gson.toJson(apiResult);
		}
		try {
			PageInfo<Agent> pageInfo=systemConfigService.queryAgent(map);
			if(pageInfo!=null && pageInfo.getList().size()>0){
				JSONObject tbJson = new JSONObject();
				tbJson.put("total",pageInfo.getTotal());
				tbJson.put("rows",pageInfo.getList());
				apiResult.setStatus("1");
				apiResult.setReason("操作成功");
				apiResult.setData(tbJson);
			}else if(pageInfo!=null && pageInfo.getList().size()==0){
				JSONObject tbJson = new JSONObject();
				tbJson.put("total",0);
				tbJson.put("rows",null);
				apiResult.setStatus("1");
				apiResult.setReason("暂无数据");
				apiResult.setData(tbJson);
			}else {
				apiResult.setStatus("0");
				apiResult.setReason("操作失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			apiResult.setStatus("-1");
			apiResult.setErrorMessage(e.getMessage());
			apiResult.setReason("操作异常");
			return gson.toJson(apiResult);
		}

		return gson.toJson(apiResult);
	}

	@ApiOperation(value = "查询单个Agent方式采集配置信息", httpMethod = "POST")
	@ResponseBody
	@RequestMapping(value = "/getAgent", method = RequestMethod.POST, produces = "*/*")
	public String getAgent(@ApiParam(value = "Json字符串") @RequestBody String data){
		ApiResultModel apiResult = new ApiResultModel();
		Agent entity= null;
		try {
			entity= gson.fromJson(data,Agent.class);
		}catch (JSONException jsonException){
			apiResult.setReason("json格式错误");
			apiResult.setStatus("-1");
			apiResult.setErrorMessage(jsonException.getMessage());
			return gson.toJson(apiResult);
		}
		try {
			entity =systemConfigService.getAgent(entity);
			apiResult.setStatus("1");
			apiResult.setReason("操作成功");
			apiResult.setData(entity);
		} catch (Exception e) {
			e.printStackTrace();
			apiResult.setStatus("-1");
			apiResult.setErrorMessage(e.getMessage());
			apiResult.setReason("操作异常");
			return gson.toJson(apiResult);
		}

		return gson.toJson(apiResult);
	}

	@ApiOperation(value = "查询目标数据库连接配置列表", httpMethod = "POST")
	@ResponseBody
	@RequestMapping(value = "/listDataSource", method = RequestMethod.POST, produces = "*/*")
	public String listDataSource(@ApiParam(value = "Json字符串") @RequestBody String data){
		ApiResultModel apiResult = new ApiResultModel();
		String rstStr="";
		Map<String,Object> map= null;
		try {
			map= gson.fromJson(data,HashMap.class);
		}catch (JSONException jsonException){
			apiResult.setReason("json格式错误");
			apiResult.setStatus("-1");
			apiResult.setErrorMessage(jsonException.getMessage());
			return gson.toJson(apiResult);
		}
		try {
			PageInfo<DataSource> pageInfo=systemConfigService.queryDataSource(map);
			if(pageInfo!=null && pageInfo.getList().size()>0){
				JSONObject tbJson = new JSONObject();
				tbJson.put("total",pageInfo.getTotal());
				tbJson.put("rows",pageInfo.getList());
				apiResult.setStatus("1");
				apiResult.setReason("操作成功");
				apiResult.setData(tbJson);
			}else if(pageInfo!=null && pageInfo.getList().size()==0){
				JSONObject tbJson = new JSONObject();
				tbJson.put("total",0);
				tbJson.put("rows",null);
				apiResult.setStatus("1");
				apiResult.setReason("暂无数据");
				apiResult.setData(tbJson);
			}else {
				apiResult.setStatus("1");
				apiResult.setReason("暂无数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
			apiResult.setStatus("-1");
			apiResult.setErrorMessage(e.getMessage());
			apiResult.setReason("操作异常");
			return gson.toJson(apiResult);
		}

		return gson.toJson(apiResult);
	}

	@ApiOperation(value = "查询单个目标数据库连接配置信息", httpMethod = "POST")
	@ResponseBody
	@RequestMapping(value = "/getDataSource", method = RequestMethod.POST, produces = "*/*")
	public String getDataSource(@ApiParam(value = "Json字符串") @RequestBody String data){
		ApiResultModel apiResult = new ApiResultModel();
		DataSource entity= null;
		try {
			entity= gson.fromJson(data,DataSource.class);
		}catch (JSONException jsonException){
			apiResult.setReason("json格式错误");
			apiResult.setStatus("-1");
			apiResult.setErrorMessage(jsonException.getMessage());
			return gson.toJson(apiResult);
		}
		try {
			entity =systemConfigService.getDataSource(entity);
			apiResult.setStatus("1");
			apiResult.setReason("操作成功");
			apiResult.setData(entity);
		} catch (Exception e) {
			e.printStackTrace();
			apiResult.setStatus("-1");
			apiResult.setErrorMessage(e.getMessage());
			apiResult.setReason("操作异常");
			return gson.toJson(apiResult);
		}

		return gson.toJson(apiResult);
	}

	@ApiOperation(value = "查询安管系统访问授权信息登记列表", httpMethod = "POST")
	@ResponseBody
	@RequestMapping(value = "/listRegister", method = RequestMethod.POST, produces = "*/*")
	public String listRegister(@ApiParam(value = "Json字符串") @RequestBody String data){
		ApiResultModel apiResult = new ApiResultModel();
		String rstStr="";
		Map<String,Object> map= null;
		try {
			map = gson.fromJson(data,HashMap.class);
		}catch (JSONException jsonException){
			apiResult.setReason("json格式错误");
			apiResult.setStatus("-1");
			apiResult.setErrorMessage(jsonException.getMessage());
			return gson.toJson(apiResult);
		}
		try {
			PageInfo<Register> pageInfo=systemConfigService.queryRegister(map);
			if(pageInfo!=null && pageInfo.getList().size()>0){
				JSONObject tbJson = new JSONObject();
				tbJson.put("total",pageInfo.getTotal());
				tbJson.put("rows",pageInfo.getList());
				apiResult.setStatus("1");
				apiResult.setReason("操作成功");
				apiResult.setData(tbJson);
			}else if(pageInfo!=null && pageInfo.getList().size()==0){
				JSONObject tbJson = new JSONObject();
				tbJson.put("total",0);
				tbJson.put("rows",null);
				apiResult.setStatus("1");
				apiResult.setReason("暂无数据");
				apiResult.setData(tbJson);
			}else {
				apiResult.setStatus("1");
				apiResult.setReason("暂无数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
			apiResult.setStatus("-1");
			apiResult.setErrorMessage(e.getMessage());
			apiResult.setReason("操作异常");
			return gson.toJson(apiResult);
		}

		return gson.toJson(apiResult);
	}

	@ApiOperation(value = "查询单个安管系统访问授权信息登记信息", httpMethod = "POST")
	@ResponseBody
	@RequestMapping(value = "/getRegister", method = RequestMethod.POST, produces = "*/*")
	public String getRegister(@ApiParam(value = "Json字符串") @RequestBody String data){
		ApiResultModel apiResult = new ApiResultModel();
		String rstStr="";
		Register entity= null;
		try {
			entity= gson.fromJson(data,Register.class);
		}catch (JSONException jsonException){
			apiResult.setReason("json格式错误");
			apiResult.setStatus("-1");
			apiResult.setErrorMessage(jsonException.getMessage());
			return gson.toJson(apiResult);
		}
		try {
			entity =systemConfigService.getRegister(entity);
			apiResult.setData(entity);
			apiResult.setStatus("1");
			apiResult.setReason("操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			apiResult.setStatus("-1");
			apiResult.setErrorMessage(e.getMessage());
			apiResult.setReason("操作异常");
			return gson.toJson(apiResult);
		}
		return rstStr;
	}

	@ApiOperation(value = "查询SMB/NFS共享方式采集配置列表", httpMethod = "POST")
	@ResponseBody
	@RequestMapping(value = "/listShareProtocol", method = RequestMethod.POST, produces = "*/*")
	public String listShareProtocol(@ApiParam(value = "Json字符串") @RequestBody String data){
		ApiResultModel apiResult = new ApiResultModel();
		String rstStr="";
		Map<String,Object> map= null;
		try {
			map= gson.fromJson(data,HashMap.class);
		}catch (JSONException jsonException){
			apiResult.setReason("json格式错误");
			apiResult.setStatus("-1");
			apiResult.setErrorMessage(jsonException.getMessage());
			return gson.toJson(apiResult);
		}
		try {
			PageInfo<ShareProtocol> pageInfo=systemConfigService.queryShareProtocol(map);
			if(pageInfo!=null && pageInfo.getList().size()>0){
				JSONObject tbJson = new JSONObject();
				tbJson.put("total",pageInfo.getTotal());
				tbJson.put("rows",pageInfo.getList());
				apiResult.setStatus("1");
				apiResult.setReason("操作成功");
				apiResult.setData(tbJson);
			} else if(pageInfo!=null && pageInfo.getList().size()==0){
				JSONObject tbJson = new JSONObject();
				tbJson.put("total",0);
				tbJson.put("rows",null);
				apiResult.setStatus("1");
				apiResult.setReason("暂无数据");
				apiResult.setData(tbJson);
			} else{
				apiResult.setStatus("0");
				apiResult.setReason("获取失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			apiResult.setStatus("-1");
			apiResult.setErrorMessage(e.getMessage());
			apiResult.setReason("操作异常");
			return gson.toJson(apiResult);
		}
		return gson.toJson(apiResult);
	}

	@ApiOperation(value = "查询单个SMB/NFS共享方式采集配置信息", httpMethod = "POST")
	@ResponseBody
	@RequestMapping(value = "/getShareProtocol", method = RequestMethod.POST, produces = "*/*")
	public String getShareProtocol(@ApiParam(value = "Json字符串") @RequestBody String data){
		ApiResultModel apiResult = new ApiResultModel();
		String rstStr="";
		ShareProtocol entity= null;
		try {
			entity= gson.fromJson(data,ShareProtocol.class);
		}catch (JSONException jsonException){
			apiResult.setReason("json格式错误");
			apiResult.setStatus("-1");
			apiResult.setErrorMessage(jsonException.getMessage());
			return gson.toJson(apiResult);
		}
		try {
			entity =systemConfigService.getShareProtocol(entity);
			apiResult.setData(entity);
			apiResult.setReason("操作成功");
			apiResult.setStatus("1");
		} catch (Exception e) {
			e.printStackTrace();
			apiResult.setStatus("-1");
			apiResult.setErrorMessage(e.getMessage());
			apiResult.setReason("操作异常");
			return gson.toJson(apiResult);
		}

		return rstStr;
	}
}
