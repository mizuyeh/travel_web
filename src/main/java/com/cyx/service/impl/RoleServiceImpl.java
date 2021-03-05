package com.cyx.service.impl;

import com.cyx.dao.RoleDao;
import com.cyx.entity.Role;
import com.cyx.service.ResourceService;
import com.cyx.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description
 * @date 2021/3/3
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private ResourceService resourceService;

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public Role findById(Long id) {
        return roleDao.findById(id);
    }

    @Override
    public Set<String> findRoles(Long... ids) {
        Set<String> roles = new HashSet<>();
        //根据ids查找对应的多个角色
        for (Long id : ids) {
            Role role = findById(id);
            //如果找到角色，就找出对应的角色名
            if(role != null) {
                roles.add(role.getName());
            }
        }
        return roles;
    }

    @Override
    public Set<String> findPermissions(Long[] ids) {
        Set<Long> resourceIds = new HashSet<Long>();
        for (Long id : ids) {
            Role role = findById(id);
            if(role != null) {
                resourceIds.addAll(role.getResourceIdsList());
            }
        }
        return resourceService.findPermissions(resourceIds);
    }
}
