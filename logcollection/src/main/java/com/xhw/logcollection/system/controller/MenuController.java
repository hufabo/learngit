package com.xhw.logcollection.system.controller;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xhw.logcollection.model.api.ApiResultModel;
import com.xhw.logcollection.model.dto.MenuDto;
import com.xhw.logcollection.system.entity.Menu;
import com.xhw.logcollection.system.service.IMenuServ;
import com.xhw.logcollection.util.DateJsonValueProcessor;
import com.xhw.logcollection.util.JSON;
import com.xhw.logcollection.util.StringUtil;
import com.xhw.logcollection.util.UUIDGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @version 0.0.1
 * @Author wanghaiyang
 * @Description 菜单管理
 * @Date 2018/2/7 15:25
 * @Modified By:
 */
@Api("菜单管理")
@RestController
@RequestMapping("api/menu")
public class MenuController {
    private Gson gson = new GsonBuilder().serializeNulls().create();

    @Autowired
    private IMenuServ menuServ;

    @ApiOperation(value = "添加菜单信息", httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "*/*")
    public String save(@ApiParam(value = "Json字符串") @RequestBody String data){
        ApiResultModel apiResult = new ApiResultModel();
        try{
            Menu menu = gson.fromJson(data,Menu.class);
            if(menu.getCdmc()==null || menu.getCdmc().equals("")){
                return gson.toJson(new ApiResultModel("0","","菜单名称不能为空",""));
            }
            if(menu.getCdxh()==null){
                return gson.toJson(new ApiResultModel("0","","菜单编号不能为空",""));
            }
            menu.setCdid(UUIDGenerator.getUUID());
            menu.setCjrq(new Date());
            Map<String,Object> paraMap = new HashMap<>();
            paraMap.put("cdmc",StringUtil.NVLLIF(menu.getCdmc(),""));
            PageInfo<Menu> pageInfo = menuServ.listMenuByCondition(paraMap);
            if(pageInfo!=null && pageInfo.getList().size()>0){
                return gson.toJson(new ApiResultModel("0","","菜单名称已存在，请重新输入",""));
            }
            paraMap.clear();
            paraMap.put("cdxh", menu.getCdxh());
            pageInfo = menuServ.listMenuByCondition(paraMap);
            if(pageInfo!=null && pageInfo.getList().size()>0){
                return gson.toJson(new ApiResultModel("0","","菜单序号已存在，请重新输入",""));
            }
            int flag = menuServ.save(menu);
            if(flag>0){
                apiResult.setStatus("1");
                apiResult.setReason("添加成功");
            }else {
                apiResult.setStatus("0");
                apiResult.setData("添加失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            apiResult.setStatus("-1");
            apiResult.setReason("系统异常，请联系管理员");
        }
        return gson.toJson(apiResult);
    }

    @ApiOperation(value = "根据条件获取菜单列表", httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "*/*")
    public String list(@ApiParam(value = "Json字符串") @RequestBody String data){
        ApiResultModel apiResult = new ApiResultModel();
        try{
            Map paraMap = gson.fromJson(data,HashMap.class);
            PageInfo<Menu> pageInfo = menuServ.listMenuByCondition(paraMap);
            if(pageInfo!=null &&pageInfo.getList().size()>0){
                apiResult.setStatus("1");
                apiResult.setReason("添加成功");

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

    @ApiOperation(value = "根据层级获取菜单列表", httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/listbylevel", method = RequestMethod.POST, produces = "*/*")
    public String listByLevel(@ApiParam(value = "Json字符串") @RequestBody String data){
        ApiResultModel apiResult = new ApiResultModel();
        try{
            Map paraMap = gson.fromJson(data,HashMap.class);
            List<MenuDto> menuDtoList = menuServ.listAllByLevel();
            if(menuDtoList!=null && menuDtoList.size()>0){
                apiResult.setStatus("1");
                apiResult.setReason("添加成功");
                apiResult.setData(this.listToArr(menuDtoList));
            }else if(menuDtoList!=null && menuDtoList.size()==0) {
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

    @ApiOperation(value = "批量删除菜单", httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/remove", method = RequestMethod.POST, produces = "*/*")
    public String removeList(@ApiParam(value = "Json字符串") @RequestBody String data){
        ApiResultModel apiResult = new ApiResultModel();
        try{
            JSONObject idObj = gson.fromJson(data,JSONObject.class);
            JSONArray idArr = idObj.getJSONArray("ids");
            List<String> ids = new ArrayList<String>();
            if(idArr==null || idArr.size()<1){
                return gson.toJson(new ApiResultModel("0","","菜单编号不能为空",""));
            }
            for (int i = 0;i<idArr.size();i++){
                ids.add(idArr.getString(i));
            }
            boolean flag = menuServ.removeList(ids);
            if(flag){
                apiResult.setStatus("1");
                apiResult.setReason("删除成功");
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

    @ApiOperation(value = "修改菜单", httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "*/*")
    public String update(@ApiParam(value = "Json字符串") @RequestBody String data){
        ApiResultModel apiResult = new ApiResultModel();
        try{
            Menu menu = gson.fromJson(data,Menu.class);
            if(menu.getCdmc()==null || menu.getCdmc().equals("")){
                return gson.toJson(new ApiResultModel("0","","菜单名称不能为空",""));
            }
            if(menu.getCdid()==null || menu.getCdid().equals("")){
                return gson.toJson(new ApiResultModel("0","","菜单编号不能为空",""));
            }
            int flag = menuServ.update(menu);
            if(flag>0){
                apiResult.setStatus("1");
                apiResult.setReason("修改成功");
            }else {
                apiResult.setStatus("0");
                apiResult.setReason("修改失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            apiResult.setStatus("-1");
            apiResult.setReason("系统异常，请联系管理员");
        }
        return gson.toJson(apiResult);
    }

    /**
     * 递归实现菜单列表转为json格式
     * @Author:wanghaiyang
     * @Date: 2018/2/27 13:48
     * @params  menuDtoList 菜单列表
     * @Modified by:
     */
    public JSONArray listToArr(List<MenuDto> menuDtoList){
        JSONArray menuArr = new JSONArray();
        for(int i = 0;i<menuDtoList.size();i++){
            MenuDto menuDto = menuDtoList.get(i);
            JSONObject menu = new JSONObject();
            menu.put("id",menuDto.getCdid());
            menu.put("name",menuDto.getCdmc());
            List<MenuDto> childMenus = menuDto.getChildMenu();
            if(childMenus!=null && childMenus.size()>0){
                menu.put("children",this.listToArr(childMenus));
            }
            menuArr.add(menu);
        }
        return menuArr;
    }
}
