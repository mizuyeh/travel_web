package com.cyx.service;

import com.cyx.entity.Order;
import com.cyx.entity.OrderAdd;

import java.util.List;

/**
 * @Description
 * @date 2021/2/27
 */
public interface OrderService {
    List<Order> findAll(int pageNum, int pageSize) throws Exception;

    Order findById(String id) throws Exception;

    int delete(String orderNum) throws Exception;

    int updateForeignKeyCheck(int status) throws Exception;

    int save(OrderAdd orderAdd) throws Exception;
}
