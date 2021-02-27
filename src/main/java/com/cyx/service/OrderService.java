package com.cyx.service;

import com.cyx.entity.Order;

import java.util.List;

/**
 * @Description
 * @date 2021/2/27
 */
public interface OrderService {
    List<Order> findAll() throws Exception;
}
