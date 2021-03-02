package com.cyx.service;

import com.cyx.dao.OrderTravelerDao;
import com.cyx.dao.TravelerDao;
import com.cyx.entity.Order;
import com.cyx.entity.Product;
import com.cyx.entity.Traveler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @Autowired
    private TravelerDao travelerDao;

    @Autowired
    private OrderTravelerDao orderTravelerDao;

    @Test
    public void testFindAll() throws Exception {
        List<Product> products = productService.findAll();
        for(Product product : products) {
            System.out.println(product);
        }
    }

    @Test
    public void testFindAllOrder() throws Exception {
        List<Order> orders = orderService.findAll(1,2);
        for (Order order : orders) {
            System.out.println(order);
        }
    }

    @Test
    public void testFindOrderById() throws Exception {
        Order order = orderService.findById("08753cfa792911ebb96d005056c00001");
        /*List<Traveler> travelers = travelerDao.findById("2eed19c178c611ebb96d005056c00001");
        for (Traveler traveler : travelers) {
            System.out.println("-----------cyx"+traveler);
        }*/
        System.out.println("-----------cyx"+order);
    }

    @Test
    public void testDeleteOrder() throws Exception {
        orderService.updateForeignKeyCheck(0);
        int delete = orderService.delete("001");
        orderService.updateForeignKeyCheck(1);
        System.out.println("---------------------删除结果：" + delete);
    }

    @Test
    public void testOrderTravelerSave() throws Exception {
        orderTravelerDao.save("415615257b1611eb9a9e005056c00001", "4150737a7b1611eb9a9e005056c00001");
        System.out.println("-------------TAG: " +
                orderTravelerDao.save("415615257b1611eb9a9e005056c00001", "4153314f7b1611eb9a9e005056c00001"));
    }
}
