package com.xhw.logcollection.system.service;


import com.github.pagehelper.PageInfo;
import com.xhw.logcollection.system.entity.Menu;
import com.xhw.logcollection.system.entity.Role;
import com.xhw.logcollection.system.entity.RoleMenuMap;

import java.util.List;
import java.util.Map;

/*
 * @Author:wanghaiyang
 * @Description:角色服务接口
 * @Date 2018/2/7 11:06
 * @Modified By:
 */
public interface IRoleServ {

    /**
     * @Author:wanghaiyang
     * @Description:添加角色
     * @Date: 2018/2/7 11:09
     * @params  role
     */
    int save(Role role);

    /**
     * @Author:wanghaiyang
     * @Description:根据条件获取角色列表
     * @Date: 2018/2/7 11:10
     * @params  paraMap
     */
    PageInfo<Role> listByCondition(Map<String,Object> paraMap);

    /**
     * 给角色授权菜单
     * @Author:wanghaiyang
     * @Date: 2018/2/7 15:40
     * @params  roleMenuMapList
     * @Modified by:
     */
    int addMenu(List<RoleMenuMap> roleMenuMapList);

    /**
     * 修改角色信息
     * @Author:wanghaiyang
     * @Date: 2018/2/27 15:55
     * @params  role 角色信息
     * @Modified by:
     */
    int updateRole(Role role);

    /**
     * 根据角色获取菜单列表
     * @Author wanghaiyang
     * @param  jsid
     * @Date 2018/4/2 15:34
     */
    List<RoleMenuMap> listMenuByRole(String jsid);
}
