package com.cyx.dao;

import com.cyx.entity.Order;
import org.apache.ibatis.annotations.Select;
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

    Order findById(String id) throws Exception;

    //通过编号查找id
    String findId(String orderNum) throws Exception;

    int delete(String orderNum) throws Exception;

    int updateForeignKeyCheck(int status) throws Exception;

    int save(Order order) throws Exception;
}
