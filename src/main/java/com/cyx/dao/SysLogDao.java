package com.cyx.dao;

import com.cyx.entity.SysLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description
 * @date 2021/3/5
 */
public interface SysLogDao {
    @Insert("insert sys_log(visitTime, username, ip, url, executionTime, method) " +
            "values(#{visitTime}, #{username}, #{ip}, #{url}, #{executionTime}, #{method})")
    void save(SysLog sysLog);

    @Select("select * from sys_log order by visitTime")
    List<SysLog> findAll();
}
