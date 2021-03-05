package com.cyx.service;

import com.cyx.entity.SysLog;

import java.util.List;

/**
 * @Description
 * @date 2021/3/5
 */
public interface SysLogService {
    void save(SysLog sysLog);

    List<SysLog> findAll();
}
