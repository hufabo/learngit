package com.xhw.logcollection.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xhw.logcollection.system.entity.Menu;
import com.xhw.logcollection.system.entity.Role;
import com.xhw.logcollection.system.entity.RoleMenuMap;
import com.xhw.logcollection.system.mapper.MenuMapper;
import com.xhw.logcollection.system.mapper.RoleMapper;
import com.xhw.logcollection.system.mapper.RoleMenuMapMapper;
import com.xhw.logcollection.system.service.IRoleServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author:wanghaiyang
 * @Description:角色服务实现
 * @Date 2018/2/7 11:40
 * @Modified By:
 */
@Service
public class RoleServImpl implements IRoleServ {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuMapMapper roleMenuMapMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public int save(Role role) {
        return roleMapper.insertSelective(role);
    }

    @Override
    public PageInfo<Role> listByCondition(Map<String, Object> paraMap) {
        if(paraMap.get("curentPage")!=null && !paraMap.get("curentPage").toString().equals("")){
            PageHelper.startPage(Integer.valueOf(paraMap.get("curentPage").toString()),Integer.valueOf(paraMap.get("pageSize").toString()));
        }
        List<Role> roles = roleMapper.listByCondition(paraMap);
        PageInfo<Role> pageInfo = new PageInfo<Role>(roles);
        return pageInfo;
    }

    @Override
    public int addMenu(List<RoleMenuMap> roleMenuMapList) {
        roleMenuMapMapper.removeByRole(roleMenuMapList.get(0).getJsid());
        return roleMenuMapMapper.inserList(roleMenuMapList);
    }

    /**
     * 修改角色信息
     *
     * @param role
     * @Author:wanghaiyang
     * @Date: 2018/2/27 15:55
     * @params role 角色信息
     * @Modified by:
     */
    @Override
    public int updateRole(Role role) {
        return roleMapper.updateByPrimaryKeySelective(role);
    }

    /**
     * 根据角色获取菜单列表
     *
     * @param jsid
     * @Author wanghaiyang
     * @Date 2018/4/2 15:34
     */
    @Override
    public List<RoleMenuMap> listMenuByRole(String jsid) {
        return roleMenuMapMapper.listByRole(jsid);
    }

}
