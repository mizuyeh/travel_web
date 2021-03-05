package com.cyx.service;

import com.cyx.entity.Resource;

import java.util.List;
import java.util.Set;

/**
 * @Description
 * @date 2021/3/3
 */
public interface ResourceService {
    List<Resource> findAll();

    Resource findById(Long id);

    /**
     * 根据id查找资源对应的权限
     * @Param [resourceIds]
     * @Return java.util.Set<java.lang.String>
     */
    Set<String> findPermissions(Set<Long> resourceIds);
}
