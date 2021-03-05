package com.cyx.controller;

import com.cyx.entity.SysLog;
import com.cyx.service.SysLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @Description
 * @date 2021/3/5
 */
@Controller
@RequestMapping("/sysLog")
public class SysLogController {
    @Autowired
    private SysLogService sysLogService;

    @RequiresPermissions("sys_log:view")
    @RequestMapping("/findAll.do")
    public ModelAndView findAll() {
        ModelAndView modelAndView = new ModelAndView();
        List<SysLog> sysLogs = sysLogService.findAll();
        modelAndView.addObject("sysLogs", sysLogs);
        modelAndView.setViewName("sysLog-list");
        return modelAndView;
    }
}
