package com.xhw.logcollection.model.dto;

import com.xhw.logcollection.system.entity.Role;
import com.xhw.logcollection.system.entity.User;

import java.util.List;

/**
 * @Author:wanghaiyang
 * @Description:用户权限数据传输对象
 * @Date 2018/2/7 13:12
 * @Modified By:
 */
public class UserAuthDto extends User {
    /**
     * 角色列表
     */
    private List<Role> roleList;

    /**
     * 菜单列表
     */
    private List<MenuDto> menuList;

    /**
     * 角色名称集合
     */
    private List<String> roleNameList;

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public List<MenuDto> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<MenuDto> menuList) {
        this.menuList = menuList;
    }

    public List<String> getRoleNameList() {
        return roleNameList;
    }

    public void setRoleNameList(List<String> roleNameList) {
        this.roleNameList = roleNameList;
    }
}
