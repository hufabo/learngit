package com.xhw.logcollection.system.mapper;

import com.xhw.logcollection.system.entity.User;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface UserMapper extends Mapper<User> {

    /**
     * 根据条件获取用户列表
     * @Author:wanghaiyang
     * @Date: 2018/2/7 17:17
     * @params  paraMap
     * @Modified by:
     */
    List<User> listByCondition(Map<String,Object> paraMap);
}