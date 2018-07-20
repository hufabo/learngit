package com.xhw.logcollection.system.mapper;

import com.xhw.logcollection.system.entity.Role;
import com.xhw.logcollection.system.entity.RoleMenuMap;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends Mapper<Role> {

    /**
     * @Author:wanghaiyang
     * @Description:根据条件获取角色列表
     * @Date: 2018/2/7 11:21
     * @params  paraMap
     */
    List<Role> listByCondition(Map<String,Object> paraMap);

}