package com.cyx.service.impl;

import com.cyx.dao.OrderDao;
import com.cyx.entity.Order;
import com.cyx.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @date 2021/2/27
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public List<Order> findAll() throws Exception {
        return orderDao.findAll();
    }
}
