package com.cyx.dao;

import com.cyx.entity.Role;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @date 2021/3/3
 */
public interface RoleDao {
    @Select("select * from role")
    List<Role> findAll();

    @Select("select * from role where id = #{id}")
    Role findById(Long id);
}
