package com.xhw.logcollection.system.mapper;

import com.xhw.logcollection.system.entity.UserRoleMap;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserRoleMapMapper extends Mapper<UserRoleMap> {

    /**
     * 给用户授权角色
     * @Author:wanghaiyang
     * @Date: 2018/2/7 15:32
     * @params  userRoleMapList
     * @Modified by:
     */
    int insertList(List<UserRoleMap> userRoleMapList);

    /**
     * 根据用户删除用户角色关联
     * @Author:wanghaiyang
     * @Date: 2018/2/7 15:45
     * @params  roleId
     * @Modified by:
     */
    int removeByUser(String yhid);

    /**
     * 根据用户ID获取用户角色关联
     * @Author wanghaiyang
     * @param  yhid
     * @Date 2018/4/2 15:51
     */
    List<UserRoleMap> listByUser(String yhid);
}