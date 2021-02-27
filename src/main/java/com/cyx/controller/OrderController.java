package com.cyx.controller;

import com.cyx.entity.Order;
import com.cyx.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @Description
 * @date 2021/2/27
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/findAll.do")
    public ModelAndView findAll() throws Exception {
        ModelAndView view = new ModelAndView();
        List<Order> orders = orderService.findAll();
        view.addObject("orderList", orders);
        view.setViewName("order-list");
        return view;
    }
}
