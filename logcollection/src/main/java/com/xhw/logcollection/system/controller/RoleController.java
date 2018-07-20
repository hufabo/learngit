package com.xhw.logcollection.system.controller;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xhw.logcollection.model.api.ApiResultModel;
import com.xhw.logcollection.model.dto.MenuDto;
import com.xhw.logcollection.system.entity.Menu;
import com.xhw.logcollection.system.entity.Role;
import com.xhw.logcollection.system.entity.RoleMenuMap;
import com.xhw.logcollection.system.service.IMenuServ;
import com.xhw.logcollection.system.service.IRoleServ;
import com.xhw.logcollection.util.JSON;
import com.xhw.logcollection.util.UUIDGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @version 0.0.1
 * @Author wanghaiyang
 * @Description 角色管理控制层
 * @Date 2018/2/7 15:24
 * @Modified By:
 */
@Api("角色管理")
@RestController
@RequestMapping("api/role")
public class RoleController {
    private Gson gson = new GsonBuilder().serializeNulls().create();

    @Autowired
    private IRoleServ roleServ;

    @Autowired
    private IMenuServ menuServ;

    @ApiOperation(value = "添加角色信息", httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "*/*")
    public String save(@ApiParam(value = "Json字符串") @RequestBody String data){
        ApiResultModel apiResult = new ApiResultModel();
        try{
            Role role = gson.fromJson(data,Role.class);
            if(role.getJsmc()==null || role.getJsmc().equals("")){
                return gson.toJson(new ApiResultModel("0","","角色名称不能为空",""));
            }
            role.setJsid(UUIDGenerator.getUUID());
            role.setCjrq(new Date());
            int flag = roleServ.save(role);
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

    @ApiOperation(value = "修改角色信息", httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "*/*")
    public String update(@ApiParam(value = "Json字符串") @RequestBody String data){
        ApiResultModel apiResult = new ApiResultModel();
        try{
            Role role = gson.fromJson(data,Role.class);
            if(role.getJsmc()==null || role.getJsmc().equals("")){
                return gson.toJson(new ApiResultModel("0","","角色名称不能为空",""));
            }
            if(role.getJsid()==null || role.getJsid().equals("")){
                return gson.toJson(new ApiResultModel("0","","角色名称不能为空",""));
            }
            int flag = roleServ.updateRole(role);
            if(flag>0){
                apiResult.setStatus("1");
                apiResult.setReason("修改成功");
            }else {
                apiResult.setStatus("0");
                apiResult.setData("修改失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            apiResult.setStatus("-1");
            apiResult.setReason("系统异常，请联系管理员");
        }
        return gson.toJson(apiResult);
    }

    @ApiOperation(value = "删除角色信息", httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/remove", method = RequestMethod.POST, produces = "*/*")
    public String remove(@ApiParam(value = "Json字符串") @RequestBody String data){
        ApiResultModel apiResult = new ApiResultModel();
        try{
            JSONObject jsidObj = gson.fromJson(data,JSONObject.class);
            JSONArray jsids = jsidObj.getJSONArray("jsid");
            if(jsids==null || jsids.size()<1){
                return gson.toJson(new ApiResultModel("0","","角色ID不能为空",""));
            }
            int flag = 0;
            for(int i = 0;i<jsids.size();i++){
                Role role = new Role();
                role.setJsid(jsids.getString(i));
                role.setZt("3");
                flag += roleServ.updateRole(role);
            }
            if(flag==jsids.size()){
                apiResult.setStatus("1");
                apiResult.setReason("删除成功");
            }else {
                apiResult.setStatus("0");
                apiResult.setData("删除失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            apiResult.setStatus("-1");
            apiResult.setReason("系统异常，请联系管理员");
        }
        return gson.toJson(apiResult);
    }

    @ApiOperation(value = "给角色授权菜单", httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/auth", method = RequestMethod.POST, produces = "*/*")
    public String auth(@ApiParam(value = "Json字符串") @RequestBody String data){
        ApiResultModel apiResult = new ApiResultModel();
        try{
            JSONObject authObj = gson.fromJson(data,JSONObject.class);
            String jsid = authObj.getString("jsid");
            JSONArray menuArr = authObj.getJSONArray("cdid");
            List<RoleMenuMap> roleMenuMapList = new ArrayList<RoleMenuMap>();
            for (int i=0;i<menuArr.size();i++){
                RoleMenuMap roleMenuMap = new RoleMenuMap();
                roleMenuMap.setJsid(jsid);
                roleMenuMap.setCdid(menuArr.getString(i));
                roleMenuMapList.add(roleMenuMap);
            }
            int flag = roleServ.addMenu(roleMenuMapList);
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

    @ApiOperation(value = "根据条件获取角色列表", httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "*/*")
    public String list(@ApiParam(value = "Json字符串") @RequestBody String data){
        ApiResultModel apiResult = new ApiResultModel();
        try{
            Map paraMap = gson.fromJson(data,HashMap.class);
            PageInfo<Menu> pageInfo = roleServ.listByCondition(paraMap);
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

    @ApiOperation(value = "根据角色获取授权菜单列表", httpMethod = "POST")
    @ResponseBody
        @RequestMapping(value = "/listmenu", method = RequestMethod.POST, produces = "*/*")
    public String listByLevel(@ApiParam(value = "Json字符串") @RequestBody String data){
        ApiResultModel apiResult = new ApiResultModel();
        try{
            Map paraMap = gson.fromJson(data,HashMap.class);
            List<MenuDto> menuDtoList = menuServ.listAllByLevel();
            List<RoleMenuMap> roleMenuMapList = roleServ.listMenuByRole(paraMap.get("jsid").toString());
            if(menuDtoList!=null && menuDtoList.size()>0){
                apiResult.setStatus("1");
                apiResult.setReason("数据获取成功");
                apiResult.setData(this.listToArr(menuDtoList,roleMenuMapList));
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

    /**
     * 递归实现菜单列表转为json格式
     * @Author:wanghaiyang
     * @Date: 2018/2/27 13:48
     * @params  menuDtoList 菜单列表
     * @Modified by:
     */
    public JSONArray listToArr(List<MenuDto> menuDtoList,List<RoleMenuMap> roleMenuMaps){
        JSONArray menuArr = new JSONArray();
        for(int i = 0;i<menuDtoList.size();i++){
            MenuDto menuDto = menuDtoList.get(i);
            JSONObject menu = new JSONObject();
            menu.put("id",menuDto.getCdid());
            menu.put("name",menuDto.getCdmc());
            menu.put("checked",this.isAuth(menuDto.getCdid(),roleMenuMaps));
            List<MenuDto> childMenus = menuDto.getChildMenu();
            if(childMenus!=null && childMenus.size()>0){
                menu.put("children",this.listToArr(childMenus,roleMenuMaps));
            }
            menuArr.add(menu);
        }
        return menuArr;
    }

    /**
     * 验证该菜单是否已被授权
     * @Author wanghaiyang
     * @param  cdid
     * @param  roleMenuMaps
     * @Date 2018/4/2 15:40
     */
    public boolean isAuth(String cdid,List<RoleMenuMap> roleMenuMaps){
        int flag = 0;
        for(int i = 0;i<roleMenuMaps.size();i++){
            if(cdid.equals(roleMenuMaps.get(i).getCdid())){
                flag++;
            }
        }
        if(flag>0){
            return true;
        }
        return false;
    }
}
