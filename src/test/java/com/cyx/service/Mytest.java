package com.cyx.service;

import com.cyx.entity.Order;
import com.cyx.entity.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Description
 * @date 2021/2/22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class Mytest {
    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Test
    public void testFindAll() throws Exception {
        List<Product> products = productService.findAll();
        for(Product product : products) {
            System.out.println(product);
        }
    }

    @Test
    public void testFindAllOrder() throws Exception {
        List<Order> orders = orderService.findAll();
        for (Order order : orders) {
            System.out.println(order);
        }
    }
}
