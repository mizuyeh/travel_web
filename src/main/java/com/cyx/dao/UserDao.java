package com.cyx.dao;

import com.cyx.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @date 2021/3/3
 */
public interface UserDao {
    @Select("select * from user")
    List<User> findAll();

    @Select("select id,username,password,salt,role_ids as roleIdsStr,locked from user where username = #{username}")
    User findByUsername(String username);

    @Insert("insert into user(username, password, salt, locked) " +
            "values(#{username}, #{password}, #{salt}, #{locked})")
    void save(User user);
}
