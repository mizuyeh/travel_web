package com.cyx.service.impl;

import com.cyx.dao.UserDao;
import com.cyx.entity.User;
import com.cyx.service.PasswordHelper;
import com.cyx.service.RoleService;
import com.cyx.service.UserService;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Description
 * @date 2021/3/3
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordHelper passwordHelper;
    @Autowired
    private RoleService roleService;

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public void register(User user) {
        //对密码进行加密
        passwordHelper.encryptPassword(user);
        userDao.save(user);
    }

    @Override
    public Set<String> findRoles(String username) {
        //1.先找对用户所有的角色ids 结果例如：1,2,3,
        User user = findByUsername(username);
        if(user == null) {
            return Collections.EMPTY_SET;
        }
        List<Long> role_ids = user.getRole_ids();
        //2.用带参数的toArray方法将集合role_ids转成Long[]
        //roleService.findRoles的参数类型是long[]
        return roleService.findRoles(role_ids.toArray(new Long[0]));
    }

    @Override
    public Set<String> findPermissions(String username) {
        //1.先找对用户所有的角色ids 结果例如：1,2,3,
        User user = findByUsername(username);
        if(user == null) {
            return Collections.EMPTY_SET;
        }
        List<Long> role_ids = user.getRole_ids();
        //2.用带参数的toArray方法将集合role_ids转成Long[]
        //roleService.findRoles的参数类型是long[]
        return roleService.findPermissions(role_ids.toArray(new Long[0]));
    }
}
