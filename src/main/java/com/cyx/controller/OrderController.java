package com.cyx.controller;

import com.cyx.entity.Order;
import com.cyx.entity.OrderAdd;
import com.cyx.entity.Product;
import com.cyx.entity.Traveler;
import com.cyx.service.OrderService;
import com.cyx.service.ProductService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import sun.rmi.server.InactiveGroupException;

import java.util.ArrayList;
import java.util.Arrays;
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

    @Autowired
    private ProductService productService;

    @RequiresPermissions("order:view")
    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(name = "page", required = true, defaultValue = "1") Integer page,
                                @RequestParam(name = "size", required = true, defaultValue = "4") Integer size) throws Exception {
        ModelAndView view = new ModelAndView();
        List<Order> orders = orderService.findAll(page, size);
        //pageInfo是一个javabean
        PageInfo<Order> pageInfo = new PageInfo<>(orders);
        view.addObject("pageInfo", pageInfo);
        view.setViewName("order-list");
        return view;
    }

    @RequiresPermissions("order:delete")
    @RequestMapping("/delete.do")
    public String delete(String check_val) throws Exception {
        List<String> orderNums = Arrays.asList(check_val.split(","));
        //先关闭外键检查
        orderService.updateForeignKeyCheck(0);
        for (String orderNum : orderNums) {
            //删除选中的订单
            int delete = orderService.delete(orderNum);
            System.out.println("受影响行数：" + delete);
        }
        //开启外键约束
        orderService.updateForeignKeyCheck(1);
        return "redirect:findAll.do";
    }

    @RequiresPermissions("order:view")
    @RequestMapping("/findById.do")
    public ModelAndView findById(String id) throws Exception {
        ModelAndView view = new ModelAndView();
        Order order = orderService.findById(id);
        view.addObject("order", order);
        view.setViewName("order-show");
        return view;
    }

    /**
     * 新建订单
     * @Param [orderAdd, travelerName, sex, phoneNum, credentialsType, credentialsNum, travelerType]
     * @Return java.lang.String
     */
    @RequiresPermissions("order:create")
    @RequestMapping("/save.do")
    public String save(OrderAdd orderAdd, String[] travelerName, String[] sex, String[] phoneNum,
                       Integer[] credentialsType, String[] credentialsNum, Integer[] travelerType) throws Exception {
        //多名旅客的信息需要填充到orderAdd的travelers中
        List<Traveler> list = new ArrayList<>();
        for (int i = 0; i < travelerName.length; i++) {
            Traveler traveler = new Traveler(travelerName[i], sex[i], phoneNum[i],
                    credentialsType[i], credentialsNum[i], travelerType[i]);
            list.add(traveler);
        }
        orderAdd.setTravelers(list);
        System.out.println(orderAdd);

        orderService.save(orderAdd);
        return "redirect:findAll.do";
    }
}
