package com.xhw.logcollection.shiro;

import com.xhw.logcollection.model.dto.MenuDto;
import com.xhw.logcollection.model.dto.UserAuthDto;
import com.xhw.logcollection.system.entity.Role;
import com.xhw.logcollection.system.entity.User;
import com.xhw.logcollection.system.service.IMenuServ;
import com.xhw.logcollection.system.service.IRoleServ;
import com.xhw.logcollection.system.service.IUserServ;
import net.sf.json.JSONArray;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.realm.AuthorizingRealm;


import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 获取用户的角色和权限信息
 * Created by bamboo on 2017/5/10.
 */
public class ShiroRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    //一般这里都写的是servic，我省略了service的接口和实现方法直接调用的dao
    @Autowired
    private IUserServ userService;

    @Autowired
    private IRoleServ roleService;

    @Autowired
    private IMenuServ menuServ;

    /**
     * 登录认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        logger.info("验证当前Subject时获取到token为：" + token.toString());
        //查出是否有此用户
        User user = new User();
        user.setYhmc(token.getUsername());
        User hasUser = userService.selectOne(user);
//        String md5Pwd = new Md5Hash("123", "lucare",2).toString();
        if (hasUser != null) {
            UserAuthDto userAuthDto = new UserAuthDto();
            userAuthDto.setYhid(hasUser.getYhid());
            userAuthDto.setYhmc(hasUser.getYhmc());
            userAuthDto.setDhhm(hasUser.getDhhm());
            userAuthDto.setDlmm(hasUser.getDlmm());
            userAuthDto.setYxdz(hasUser.getYxdz());
            userAuthDto.setBz(hasUser.getBz());
            userAuthDto.setCjrq(hasUser.getCjrq());

            // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
            Map<String,Object> paraMap = new HashMap<String,Object>();
            paraMap.put("yhid",hasUser.getYhid());
            List<Role> rlist = roleService.listByCondition(paraMap).getList();//获取用户角色
            if(rlist!=null && rlist.size()>0){
                List<String> roleStrlist=new ArrayList<String>();////用户的角色集合
                for (Role role : rlist) {
                    roleStrlist.add(role.getJsid());
                }
                List<MenuDto> mlist = menuServ.listByRole(roleStrlist);//获取用户权限

//                List<String> perminsStrlist=new ArrayList<String>();//用户的权限集合

//            for (FuncGroupDto uPermission : plist) {
//            	perminsStrlist.add(uPermission);
//			}
                userAuthDto.setRoleNameList(roleStrlist);
                userAuthDto.setRoleList(rlist);
                userAuthDto.setMenuList(mlist);
            }

            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute("user", hasUser);//成功则放入session
         // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
            return new SimpleAuthenticationInfo(userAuthDto, userAuthDto.getDlmm(), getName());
        }
        return null;
    }

    /**
     * 权限认证
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("##################执行Shiro权限认证##################");
        //获取当前登录输入的用户名，等价于(String) principalCollection.fromRealm(getName()).iterator().next();
//        String loginName = (String) super.getAvailablePrincipal(principalCollection);
        UserAuthDto user = (UserAuthDto) principalCollection.getPrimaryPrincipal();
//        //到数据库查是否有此对象
//        User user = null;// 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
//        user = userMapper.findByName(loginName);
        if (user != null) {
            //权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            //用户的角色集合
            info.addRoles(user.getRoleNameList());
            //用户的权限集合
            info.addStringPermission(JSONArray.fromObject(user.getMenuList()).toString());

            return info;
        }
        // 返回null的话，就会导致任何用户访问被拦截的请求时，都会自动跳转到unauthorizedUrl指定的地址
        return null;
    }


}
