package com.xhw.logcollection.system.service;

import com.github.pagehelper.PageInfo;
import com.xhw.logcollection.system.entity.User;
import com.xhw.logcollection.system.entity.UserRoleMap;

import java.util.List;
import java.util.Map;

public interface IUserServ {
    /**
     * @Author:wanghaiyang
     * @Description:保存用户
     * @Date: 2018/2/7 11:02
     * @params  user
     */
    int save(User user);

    /**
     * @Author:wanghaiyang
     * @Description:获取单个用户实体
     * @Date: 2018/2/7 11:05
     * @params  user
     */
    User selectOne(User user);

    /**
     * 给用户授权角色
     * @Author:wanghaiyang
     * @Date: 2018/2/7 15:30
     * @params  userRoleMap
     */
    int addRole(List<UserRoleMap> userRoleMapList);

    /**
     * 根据条件获取用户列表
     * @Author:wanghaiyang
     * @Date: 2018/2/7 17:17
     * @params  paraMap
     * @Modified by:
     */
    PageInfo<User> listByCondition(Map<String,Object> paraMap);

    /**
     * 修改用户信息
     * @Author:wanghaiyang
     * @Date: 2018/2/27 15:46
     * @params  user 用户信息
     * @Modified by:
     */
    int updateUser(User user);

    /**
     * 检查用户名
     * 是否已存在
     * @author konggang
     * @date 2018/3/30 16:36
     */
    boolean checkUserName(String username);

    /**
     * 根据用户ID获取用户角色关联
     * @Author wanghaiyang
     * @param  yhid
     * @Date 2018/4/2 15:51
     */
    List<UserRoleMap> listByUser(String yhid);

}
