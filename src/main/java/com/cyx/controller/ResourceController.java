package com.cyx.controller;

import com.cyx.entity.Resource;
import com.cyx.service.ResourceService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @Description
 * @date 2021/3/3
 */
@Controller
@RequestMapping("/resource")
public class ResourceController {
    @Autowired
    private ResourceService resourceService;

    @RequiresPermissions("resource:view")
    @RequestMapping("/findAll.do")
    public ModelAndView findAll() {
        ModelAndView modelAndView = new ModelAndView();
        List<Resource> resources = resourceService.findAll();
        modelAndView.addObject("resourceList", resources);
        modelAndView.setViewName("resource-list");
        return modelAndView;
    }
}
