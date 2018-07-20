package com.xhw.logcollection.system.service;

import com.github.pagehelper.PageInfo;
import com.xhw.logcollection.model.dto.MenuDto;
import com.xhw.logcollection.system.entity.Menu;

import java.util.List;
import java.util.Map;

/**
 * @Author:wanghaiyang
 * @Description 菜单服务接口
 * @Date 2018/2/7 11:37
 * @Modified By:
 */
public interface IMenuServ {

    /**
     * 根据层级显示所有菜单
     * @Author:wanghaiyang
     * @Date: 2018/2/27 13:38
     * @Modified by:
     */
    List<MenuDto> listAllByLevel();

    /**
     * @Author:wanghaiyang
     * @Description:根据角色获取菜单列表
     * @Date: 2018/2/7 15:20
     * @params  roleStrList
     */
    List<MenuDto> listByRole(List<String> roleStrList);

    /**
     * @Author:wanghaiyang
     * @Description:添加菜单
     * @Date: 2018/2/7 11:38
     * @params  menu
     */
    int save(Menu menu);

    /**
     * @Author:wanghaiyang
     * @Description:根据条件获取菜单列表
     * @Date: 2018/2/7 11:38
     * @params  paraMap
     */
    List<MenuDto> listByCondition(Map<String,Object> paraMap);

    /**
     * @Author:wanghaiyang
     * @Description:根据条件获取菜单列表
     * @Date: 2018/2/7 11:38
     * @params  paraMap
     */
    PageInfo<Menu> listMenuByCondition(Map<String,Object> paraMap);

    /**
     * 批量删除菜单
     * @Author:wanghaiyang
     * @Date: 2018/2/24 9:49
     * @params  ids 菜单id数组
     * @Modified by:
     */
    boolean removeList(List<String> ids);

    /**
     * 修改菜单
     * @Author:wanghaiyang
     * @Date: 2018/2/24 10:25
     * @params  menu
     * @Modified by:
     */
    int update(Menu menu);
}
