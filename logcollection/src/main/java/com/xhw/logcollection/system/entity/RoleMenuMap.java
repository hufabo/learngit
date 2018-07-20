package com.xhw.logcollection.system.entity;

import javax.persistence.*;

@Table(name = "SYS_ROLE_MENU_MAP")
public class RoleMenuMap {
    /**
     * 菜单编号
     */
    @Column(name = "CDID")
    private String cdid;

    /**
     * 角色编号
     */
    @Column(name = "JSID")
    private String jsid;

    /**
     * 获取菜单编号
     *
     * @return CDID - 菜单编号
     */
    public String getCdid() {
        return cdid;
    }

    /**
     * 设置菜单编号
     *
     * @param cdid 菜单编号
     */
    public void setCdid(String cdid) {
        this.cdid = cdid == null ? null : cdid.trim();
    }

    /**
     * 获取角色编号
     *
     * @return JSID - 角色编号
     */
    public String getJsid() {
        return jsid;
    }

    /**
     * 设置角色编号
     *
     * @param jsid 角色编号
     */
    public void setJsid(String jsid) {
        this.jsid = jsid == null ? null : jsid.trim();
    }
}