package com.cyx.service;

import com.cyx.entity.Resource;
import com.cyx.entity.Role;
import com.cyx.entity.User;

import java.util.List;
import java.util.Set;

/**
 * @Description
 * @date 2021/3/3
 */
public interface UserService {
    List<User> findAll();

    User findByUsername(String username);

    void register(User user);

    /**
     * 根据用户名查找对应的所有角色名
     * @Param [username]
     * @Return java.util.Set<com.cyx.entity.Role>
     */
    Set<String> findRoles(String username);

    /**
     * 根据用户名查找对应的角色id，
     * 根据角色id查找对应的资源id，
     * 根据资源id查找对应的权限名称，
     * 由于一个用户可能有多重角色，各个角色对应的权限可能重复，所有结果集用set
     * @Param [username]
     * @Return java.util.Set<java.lang.String>
     */
    Set<String> findPermissions(String username);
}
