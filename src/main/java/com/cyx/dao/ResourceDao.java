package com.cyx.dao;

import com.cyx.entity.Resource;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description
 * @date 2021/3/3
 */
public interface ResourceDao {
    @Select("select * from resource")
    List<Resource> findAll();

    @Select("select * from resource where id = #{id}")
    Resource findByid(Long id);
}
