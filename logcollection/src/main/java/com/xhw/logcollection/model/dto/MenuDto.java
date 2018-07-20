package com.xhw.logcollection.model.dto;

import com.xhw.logcollection.system.entity.Menu;

import java.util.List;

/**
 * @Author:wanghaiyang
 * @Description:菜单数据传输对象
 * @Date 2018/2/7 13:16
 * @Modified By:
 */
public class MenuDto extends Menu {
    /**
     * 子菜单
     */
    private List<MenuDto> childMenu;

    public List<MenuDto> getChildMenu() {
        return childMenu;
    }

    public void setChildMenu(List<MenuDto> childMenu) {
        this.childMenu = childMenu;
    }
}
