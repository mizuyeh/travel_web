package com.cyx.service.impl;

import com.cyx.dao.ResourceDao;
import com.cyx.entity.Resource;
import com.cyx.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description
 * @date 2021/3/3
 */
@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    private ResourceDao resourceDao;

    @Override
    public List<Resource> findAll() {
        return resourceDao.findAll();
    }

    @Override
    public Resource findById(Long id) {
        return resourceDao.findByid(id);
    }

    @Override
    public Set<String> findPermissions(Set<Long> resourceIds) {
        Set<String> permissions = new HashSet<String>();
        for (Long resourceId : resourceIds) {
            Resource resource = findById(resourceId);
            if (resource != null) {
                permissions.add(resource.getPermission());
            }
        }
        return permissions;
    }
}
