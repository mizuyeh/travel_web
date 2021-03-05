package com.cyx.service;

import com.cyx.entity.Role;

import java.util.List;
import java.util.Set;

/**
 * @Description
 * @date 2021/3/3
 */
public interface RoleService {
    List<Role> findAll();

    Role findById(Long id);

    /**
     * 根据id找对角色名
     * @Param [ids]
     * @Return java.util.Set<java.lang.String>
     */
    Set<String> findRoles(Long... ids);

    /**
     * 根据id先查找对应的角色，
     * 再根据角色找对应的资源id，
     * 再根据资源id找对应的权限
     * @Param [ids]
     * @Return java.util.Set<java.lang.String>
     */
    Set<String> findPermissions(Long[] ids);
}
