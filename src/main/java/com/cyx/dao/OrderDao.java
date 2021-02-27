package com.cyx.dao;

import com.cyx.entity.Order;
import org.aspectj.weaver.ast.Or;

import java.util.List;

/**
 * @Description
 * @date 2021/2/27
 */
public interface OrderDao {
    /**
     * 查询所有订单
     * @Param []
     * @Return java.util.List<com.cyx.entity.Order>
     */
    List<Order> findAll() throws Exception;
}
