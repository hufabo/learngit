package com.xhw.logcollection.system.entity;

import javax.persistence.*;

@Table(name = "SYS_USER_ROLE_MAP")
public class UserRoleMap {
    /**
     * 用户ID
     */
    @Column(name = "YHID")
    private String yhid;

    /**
     * 角色编号
     */
    @Column(name = "JSID")
    private String jsid;

    /**
     * 获取用户ID
     *
     * @return YHID - 用户ID
     */
    public String getYhid() {
        return yhid;
    }

    /**
     * 设置用户ID
     *
     * @param yhid 用户ID
     */
    public void setYhid(String yhid) {
        this.yhid = yhid == null ? null : yhid.trim();
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