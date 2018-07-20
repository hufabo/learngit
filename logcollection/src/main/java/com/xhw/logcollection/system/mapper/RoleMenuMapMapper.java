package com.xhw.logcollection.system.mapper;

import com.xhw.logcollection.system.entity.RoleMenuMap;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RoleMenuMapMapper extends Mapper<RoleMenuMap> {

    /**
     * 批量添加
     * @Author:wanghaiyang
     * @Date: 2018/2/7 15:40
     * @params  roleMenuMapList
     * @Modified by:
     */
    int inserList(List<RoleMenuMap> roleMenuMapList);

    /**
     * 根据角色删除角色菜单关联
     * @Author:wanghaiyang
     * @Date: 2018/2/7 15:45
     * @params  roleId
     * @Modified by:
     */
    int removeByRole(String roleId);

    /**
     * 根据菜单删除角色菜单关联
     * @Author:wanghaiyang
     * @Date: 2018/2/24 10:00
     * @params  ids 菜单编号
     * @Modified by:
     */
    int removeByMenu(List<String> ids);

    /**
     * 根据角色获取菜单编号列表
     * @Author wanghaiyang
     * @param roleId
     * @Date 2018/4/2 15:32
     */
    List<RoleMenuMap> listByRole(String roleId);
}