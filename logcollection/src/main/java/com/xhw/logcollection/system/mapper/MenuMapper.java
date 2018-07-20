package com.xhw.logcollection.system.mapper;

import com.xhw.logcollection.model.dto.MenuDto;
import com.xhw.logcollection.system.entity.Menu;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface MenuMapper extends Mapper<Menu> {
    /**
     * @Author:wanghaiyang
     * @Description:根据条件获取菜单列表
     * @Date: 2018/2/7 13:24
     * @params  paraMap
     */
    List<MenuDto> listByCondition(Map<String,Object> paraMap);

    /**
     * @Author:wanghaiyang
     * @Description:根据条件获取菜单列表
     * @Date: 2018/2/7 11:38
     * @params  paraMap
     */
    List<Menu> listMenuByCondition(Map<String,Object> paraMap);

    /**
     * 批量删除菜单
     * @Author:wanghaiyang
     * @Date: 2018/2/24 9:49
     * @params  ids 菜单id数组
     * @Modified by:
     */
    int removeList(List<String> ids);
}