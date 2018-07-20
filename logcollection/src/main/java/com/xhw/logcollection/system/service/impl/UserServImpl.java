package com.xhw.logcollection.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xhw.logcollection.system.entity.User;
import com.xhw.logcollection.system.entity.UserRoleMap;
import com.xhw.logcollection.system.mapper.UserMapper;
import com.xhw.logcollection.system.mapper.UserRoleMapMapper;
import com.xhw.logcollection.system.service.IUserServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/*
 * @Author:wanghaiyang
 * @Description 用户服务实现
 * @Date 2018/2/7 11:29
 * @Modified By:
 */
@Service
public class UserServImpl implements IUserServ {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapMapper userRoleMapMapper;

    @Override
    public int save(User user) {
        return userMapper.insertSelective(user);
    }

    @Override
    public User selectOne(User user) {
        return userMapper.selectOne(user);
    }

    @Override
    public int addRole(List<UserRoleMap> userRoleMapList) {
        userRoleMapMapper.removeByUser(userRoleMapList.get(0).getYhid());
        return userRoleMapMapper.insertList(userRoleMapList);
    }

    @Override
    public PageInfo<User> listByCondition(Map<String, Object> paraMap) {
        if(paraMap.get("curentPage")!=null && !paraMap.get("curentPage").toString().equals("")){
            PageHelper.startPage(Integer.valueOf(paraMap.get("curentPage").toString()),Integer.valueOf(paraMap.get("pageSize").toString()));
        }
        List<User> users = userMapper.listByCondition(paraMap);
        PageInfo<User> pageInfo = new PageInfo<User>(users);
        return pageInfo;
    }

    /**
     * 修改用户信息
     *
     * @param user
     * @Author:wanghaiyang
     * @Date: 2018/2/27 15:46
     * @params user 用户信息
     * @Modified by:
     */
    @Override
    public int updateUser(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public boolean checkUserName(String username) {
        User user = new User();
        user.setYhmc(username);
        List<User> users = userMapper.select(user);
        if(users != null && users.size() > 0){
            return false;
        }
        return true;
    }

    /**
     * 根据用户ID获取用户角色关联
     *
     * @param yhid
     * @Author wanghaiyang
     * @Date 2018/4/2 15:51
     */
    @Override
    public List<UserRoleMap> listByUser(String yhid) {
        return userRoleMapMapper.listByUser(yhid);
    }
}
