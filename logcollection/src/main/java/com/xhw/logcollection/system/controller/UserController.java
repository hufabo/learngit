package com.xhw.logcollection.system.controller;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xhw.logcollection.model.api.ApiResultModel;
import com.xhw.logcollection.model.dto.MenuDto;
import com.xhw.logcollection.model.dto.UserAuthDto;
import com.xhw.logcollection.system.entity.Menu;
import com.xhw.logcollection.system.entity.Role;
import com.xhw.logcollection.system.entity.User;
import com.xhw.logcollection.system.entity.UserRoleMap;
import com.xhw.logcollection.system.service.IRoleServ;
import com.xhw.logcollection.system.service.IUserServ;
import com.xhw.logcollection.util.JSON;
import com.xhw.logcollection.util.MD5Util;
import com.xhw.logcollection.util.UUIDGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @version 0.0.1
 * @Author wanghaiyang
 * @Description 用户管理
 * @Date 2018/2/7 15:26
 * @Modified By:
 */
@RestController
@Api("用户管理")
@RequestMapping("api/user")
public class UserController {
    private Gson gson = new GsonBuilder().serializeNulls().create();

    @Autowired
    private IUserServ userServ;

    @Autowired
    private IRoleServ roleServ;

    @ApiOperation(value = "验证用户名", httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/checkUserName", method = RequestMethod.POST, produces = "*/*")
    public String checkUserName(@ApiParam(value = "Json字符串") @RequestBody String data){
        ApiResultModel apiResult = new ApiResultModel();
        try{
            User user = gson.fromJson(data,User.class);
            String yhmc = user.getYhmc();
            if(yhmc ==null || yhmc.equals("")){
                return gson.toJson(new ApiResultModel("0","","用户名称不能为空",""));
            }
            if(userServ.checkUserName(yhmc)){
                return gson.toJson(new ApiResultModel("1","","用户名可用",""));
            }else{
                return gson.toJson(new ApiResultModel("0","","用户名已存在",""));
            }
        }catch (Exception e){
            e.printStackTrace();
            apiResult.setStatus("-1");
            apiResult.setReason("系统异常，请联系管理员");
        }
        return gson.toJson(apiResult);
    }

    @ApiOperation(value = "添加用户信息", httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "*/*")
    public String save(@ApiParam(value = "Json字符串") @RequestBody String data){
        ApiResultModel apiResult = new ApiResultModel();
        try{
            User user = gson.fromJson(data,User.class);
            if(user.getYhmc()==null || user.getYhmc().equals("")){
                return gson.toJson(new ApiResultModel("0","","用户名称不能为空",""));
            }
            if(user.getDlmm()==null || user.getDlmm().equals("")) {
                return gson.toJson(new ApiResultModel("0", "", "登录密码不能为空", ""));
            }
            if(!userServ.checkUserName(user.getYhmc())){
                return gson.toJson(new ApiResultModel("0", "", "用户已存在", ""));
            }
            user.setYhid(UUIDGenerator.getUUID());
            user.setCjrq(new Date());
            String pwd = user.getDlmm();
            user.setDlmm(MD5Util.md5Encode(pwd));
            user.setZt("1");
            int flag = userServ.save(user);
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

    @ApiOperation(value = "修改用户信息", httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "*/*")
    public String update(@ApiParam(value = "Json字符串") @RequestBody String data){
        ApiResultModel apiResult = new ApiResultModel();
        try{
            User user = gson.fromJson(data,User.class);
            if(user.getYhmc()==null || user.getYhmc().equals("")){
                return gson.toJson(new ApiResultModel("0","","用户名称不能为空",""));
            }
            if(user.getYhid()==null || user.getYhid().equals("")) {
                return gson.toJson(new ApiResultModel("0", "", "登录ID不能为空", ""));
            }
            if(user.getDlmm()!=null && !user.getDlmm().equals("")){
                String pwd = user.getDlmm();
                user.setDlmm(MD5Util.md5Encode(pwd));
            }
            int flag = userServ.updateUser(user);
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

    @ApiOperation(value = "删除用户信息", httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/remove", method = RequestMethod.POST, produces = "*/*")
    public String remove(@ApiParam(value = "Json字符串") @RequestBody String data){
        ApiResultModel apiResult = new ApiResultModel();
        try{
            JSONObject userObj = gson.fromJson(data,JSONObject.class);
            JSONArray userArr = userObj.getJSONArray("yhid");
            if(userArr==null || userArr.size()<1) {
                return gson.toJson(new ApiResultModel("0", "", "用户ID不能为空", ""));
            }
            int flag = 0;
            for(int i = 0;i<userArr.size();i++){
                User user = new User();
                user.setYhid(userArr.getString(i));
                user.setZt("3");
                flag+=userServ.updateUser(user);
            }
            if(flag==userArr.size()){
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

    @ApiOperation(value = "给用户授权角色", httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/auth", method = RequestMethod.POST, produces = "*/*")
    public String auth(@ApiParam(value = "Json字符串") @RequestBody String data){
        ApiResultModel apiResult = new ApiResultModel();
        try{
            JSONObject authObj = gson.fromJson(data,JSONObject.class);
            String yhid = authObj.getString("yhid");
            JSONArray roleArr = authObj.getJSONArray("jsid");
            List<UserRoleMap> userRoleMapList = new ArrayList<UserRoleMap>();
            for (int i=0;i<roleArr.size();i++){
                UserRoleMap userRoleMap = new UserRoleMap();
                userRoleMap.setJsid(roleArr.getString(i));
                userRoleMap.setYhid(yhid);
                userRoleMapList.add(userRoleMap);
            }
            int flag = userServ.addRole(userRoleMapList);
            if(flag>0){
                apiResult.setStatus("1");
                apiResult.setReason("授权成功");
            }else {
                apiResult.setStatus("0");
                apiResult.setData("授权失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            apiResult.setStatus("-1");
            apiResult.setReason("系统异常，请联系管理员");
        }
        return gson.toJson(apiResult);
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST,produces = "*/*")
    @ApiOperation(value = "用户登录",httpMethod = "POST")
    public String userLogin(@ApiParam(value = "参数") @RequestBody String data) {
        String restr = null;
        Gson gson = new GsonBuilder().serializeNulls().create();
        ApiResultModel apiResult = new ApiResultModel();
        try {
            Map<String, Object> paraMap = gson.fromJson(data, HashMap.class);

            if(paraMap.get("yhmc")==null || paraMap.get("yhmc").equals("")){
                return gson.toJson(new ApiResultModel("0","","","用户名不能为空"));
            }
            if(paraMap.get("dlmm")==null || paraMap.get("dlmm").equals("")){
                return gson.toJson(new ApiResultModel("0","","","密码不能为空"));
            }

            // 获取请求参数
            String policeNo = String.valueOf(paraMap.get("yhmc"));
            String password = String.valueOf(paraMap.get("dlmm"));

            User bkUser = new User();
            bkUser.setZt("1");//状态是可用的财可以登录
            // 账号赋值
            bkUser.setYhmc(policeNo);
            if(userServ.selectOne(bkUser) == null){
                apiResult.setReason("账号不存在或已被禁用");
                apiResult.setStatus("0");
            }else {
                // 密码赋值
                bkUser.setDlmm(MD5Util.md5Encode(password));
                UsernamePasswordToken token = new UsernamePasswordToken(bkUser.getYhmc(), bkUser.getDlmm());
                token.setRememberMe(false);
                Subject subject = SecurityUtils.getSubject();
                subject.login(token);
                apiResult.setData(subject.getPrincipal());
                apiResult.setStatus("1");
                apiResult.setReason("登录成功");
            }

        } catch (Exception e) {
            e.printStackTrace();
            apiResult.setReason("用户名或密码不正确");
            apiResult.setStatus("-1");
        }
        return gson.toJson(apiResult);
    }

    @RequestMapping(value = "/haslogin",method = RequestMethod.POST,produces = "*/*")
    @ApiOperation(value = "是否已经登录",httpMethod = "POST")
    public String hasLogin(@ApiParam(value = "参数") @RequestBody String data, HttpServletRequest request, HttpServletResponse response){
        Gson gson = new GsonBuilder().serializeNulls().create();
        ApiResultModel apiResult = new ApiResultModel();
        try {
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession();
            String sessionID = session.getId().toString();
            //String sessionID = json.getString("jsessionID");
            if(isAuthenticated(sessionID,request,response)){
                UserAuthDto userInfo = getUserInfo(sessionID,request,response);
                apiResult.setStatus("1");
                apiResult.setData(navTrans(userInfo));
                apiResult.setReason("已登录");
            }else {
                apiResult.setStatus("0");
                apiResult.setReason("用户登录已过期，请重新登录");
            }

        }catch (Exception e){
            e.printStackTrace();
            apiResult.setData(e.toString());
            apiResult.setReason("系统异常，请联系管理员");
            apiResult.setStatus("-1");
        }
        return gson.toJson(apiResult);
    }

    @RequestMapping(value = "/logout",method = RequestMethod.POST,produces = "*/*")
    @ApiOperation(value = "退出登录",httpMethod = "POST")
    public String logOut(@ApiParam(value = "参数") @RequestBody String data,HttpServletRequest request,HttpServletResponse response){
        Gson gson = new GsonBuilder().serializeNulls().create();
        ApiResultModel apiResult = new ApiResultModel();
        try {
            Map<String, Object> paraMap = gson.fromJson(data, HashMap.class);
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
            apiResult.setReason("退出成功");
            apiResult.setStatus("1");
        }catch (Exception e){
            e.printStackTrace();
            apiResult.setData(e.toString());
            apiResult.setReason("系统异常，请联系管理员");
            apiResult.setStatus("-1");
        }
        return gson.toJson(apiResult);
    }

    @ApiOperation(value = "根据条件获取用户列表", httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "*/*")
    public String list(@ApiParam(value = "Json字符串") @RequestBody String data){
        ApiResultModel apiResult = new ApiResultModel();
        try{
            Map paraMap = gson.fromJson(data,HashMap.class);
            PageInfo<Menu> pageInfo = userServ.listByCondition(paraMap);
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

    public boolean isAuthenticated(String sessionID,HttpServletRequest request,HttpServletResponse response){
        boolean status = false;

        SessionKey key = new WebSessionKey(sessionID,request,response);
        try{
            Session se = SecurityUtils.getSecurityManager().getSession(key);
            Object obj = se.getAttribute(DefaultSubjectContext.AUTHENTICATED_SESSION_KEY);
            if(obj != null){
                status = (Boolean) obj;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            Session se = null;
            Object obj = null;
        }

        return status;
    }

    public UserAuthDto getUserInfo(String sessionID, HttpServletRequest request, HttpServletResponse response){
        boolean status = false;
        SessionKey key = new WebSessionKey(sessionID,request,response);
        try{
            Session se = SecurityUtils.getSecurityManager().getSession(key);
            Object obj = se.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            //org.apache.shiro.subject.SimplePrincipalCollection cannot be cast to com.hncxhd.bywl.entity.manual.UserInfo
            SimplePrincipalCollection coll = (SimplePrincipalCollection) obj;
            return (UserAuthDto)coll.getPrimaryPrincipal();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
        }
        return null;
    }

    public JSONObject navTrans(UserAuthDto userAuthDto){
        JSONObject navJso = new JSONObject();
        navJso.put("userName",userAuthDto.getYhmc());
        navJso.put("userId",userAuthDto.getYhid());
        navJso.put("role",userAuthDto.getRoleNameList());
        List<MenuDto> powerList = userAuthDto.getMenuList();
        JSONArray powerArr = new JSONArray();
        if(powerList!=null && powerList.size()>0){
            navJso.put("nav",this.getPower(powerList));
        }
        return navJso;
    }

    /**
     * 递归转换菜单列表
     * @param menuDtoList
     * @return
     */
    public JSONArray getPower(List<MenuDto> menuDtoList){
        JSONArray power = new JSONArray();
        for (int i=0;i<menuDtoList.size();i++){
            JSONObject menu = new JSONObject();
            MenuDto menuDto = menuDtoList.get(i);
            menu.put("title",menuDto.getCdmc());
            if(menuDto.getCdtb()!=null && !menuDto.getCdtb().equals("")){
                menu.put("icon",menuDto.getCdtb());
            }
            if(menuDto.getCdljdz()!=null && !menuDto.getCdljdz().equals("")){
                menu.put("href",menuDto.getCdljdz());
            }
            if(menuDto.getChildMenu()!=null && menuDto.getChildMenu().size()>0){
                menu.put("children",getPower(menuDto.getChildMenu()));
            }
            power.add(menu);
        }
        return power;

    }

    @ApiOperation(value = "根据用户获取角色列表", httpMethod = "POST")
    @ResponseBody
    @RequestMapping(value = "/listrole", method = RequestMethod.POST, produces = "*/*")
    public String listRole(@ApiParam(value = "Json字符串") @RequestBody String data){
        ApiResultModel apiResult = new ApiResultModel();
        try{
            Map paraMap = gson.fromJson(data,HashMap.class);
            PageInfo<Role> pageInfo = roleServ.listByCondition(new HashMap<String,Object>());
            List<UserRoleMap> userRoleMapList = userServ.listByUser(paraMap.get("yhid").toString());
            if(pageInfo!=null &&pageInfo.getList().size()>0){
                apiResult.setStatus("1");
                apiResult.setReason("数据获取成功");
                apiResult.setData(this.getRoleArr(pageInfo.getList(),userRoleMapList));
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

    public JSONArray getRoleArr(List<Role> roles,List<UserRoleMap> roleMaps){
        JSONArray roleArr = new JSONArray();
        for (int i = 0;i<roles.size();i++){
            int flag = 0;
            Role tmpRole = roles.get(i);
            JSONObject role = new JSONObject();
            role.put("id",tmpRole.getJsid());
            role.put("name",tmpRole.getJsmc());
            for (int j = 0;j<roleMaps.size();j++){
                UserRoleMap tmpRoleMap = roleMaps.get(j);
                if(tmpRole.getJsid().equals(tmpRoleMap.getJsid())){
                    flag++;
                }
            }
            if(flag>0){
                role.put("checked",true);
            }else {
                role.put("checked",false);
            }
            roleArr.add(role);
        }
        return roleArr;
    }

}
