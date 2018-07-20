package com.xhw.logcollection.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xhw.logcollection.model.dto.MenuDto;
import com.xhw.logcollection.system.entity.Menu;
import com.xhw.logcollection.system.entity.Role;
import com.xhw.logcollection.system.mapper.MenuMapper;
import com.xhw.logcollection.system.mapper.RoleMenuMapMapper;
import com.xhw.logcollection.system.service.IMenuServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:wanghaiyang
 * @Description:菜单服务实现类
 * @Date 2018/2/7 13:37
 * @Modified By:
 */
@Service
public class MenuServImpl implements IMenuServ {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMenuMapMapper roleMenuMapMapper;

    /**
     * 根据层级显示所有菜单
     *
     * @Author:wanghaiyang
     * @Date: 2018/2/27 13:38
     * @Modified by:
     */
    @Override
    public List<MenuDto> listAllByLevel() {
        List<MenuDto> menuDtoList = new ArrayList<>();
        Map<String,Object> paraMap = new HashMap<>();
        paraMap.put("fjcdid","0");
        List<MenuDto> menuList = this.listByCondition(paraMap);
        if(menuList!=null && menuList.size()>0){
            menuDtoList = getchildMenu(menuList,paraMap);
        }
        return menuDtoList;
    }

    public List<MenuDto> listByRole(List<String> roleStrList){
        Map<String,Object> paraMap = new HashMap<>();
        paraMap.put("jsid",roleStrList);
        List<MenuDto> menuDtoList = new ArrayList<>();
        paraMap.put("fjcdid","0");
        List<MenuDto> menuList = this.listByCondition(paraMap);
        if(menuList!=null && menuList.size()>0){
            menuDtoList = getchildMenu(menuList,paraMap);
        }
        return menuDtoList;
    }

    /**
     * 递归实现菜单权限列表
     * @Author:wanghaiyang
     * @Date: 2018/2/7 15:43
     * @params  * @param null
     * @Modified by:
     */
    public List<MenuDto> getchildMenu(List<MenuDto> menuDtoList,Map<String, Object> paraMap){
        for (int i = 0;i<menuDtoList.size();i++){
            paraMap.put("fjcdid",menuDtoList.get(i).getCdid());
            List<MenuDto> menuList = this.listByCondition(paraMap);
            if(menuList!=null && menuList.size()>0){
                menuDtoList.get(i).setChildMenu(menuList);
                getchildMenu(menuList,paraMap);
            }
        }
        return menuDtoList;
    }

    @Override
    public int save(Menu menu) {
        return menuMapper.insertSelective(menu);
    }

    @Override
    public List<MenuDto> listByCondition(Map<String, Object> paraMap) {
        return menuMapper.listByCondition(paraMap);
    }

    @Override
    public PageInfo<Menu> listMenuByCondition(Map<String, Object> paraMap) {
        if(paraMap.get("curentPage")!=null && !paraMap.get("curentPage").toString().equals("")){
            PageHelper.startPage(Integer.valueOf(paraMap.get("curentPage").toString()),Integer.valueOf(paraMap.get("pageSize").toString()));
        }
        List<Menu> menuList = menuMapper.listMenuByCondition(paraMap);
        PageInfo<Menu> pageInfo = new PageInfo<Menu>(menuList);
        return pageInfo;
    }

    @Override
    public boolean removeList(List<String> ids) {
        try{
            int flag1 = menuMapper.removeList(ids);
            int flag2 = roleMenuMapMapper.removeByMenu(ids);
            if(flag1>0){
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return false;
    }

    /**
     * 修改菜单
     *
     * @param menu
     * @Author:wanghaiyang
     * @Date: 2018/2/24 10:25
     * @params menu
     * @Modified by:
     */
    @Override
    public int update(Menu menu) {
        return menuMapper.updateByPrimaryKeySelective(menu);
    }


}
