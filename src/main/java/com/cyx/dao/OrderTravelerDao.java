package com.cyx.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * @Description 对应order_traveler表
 * @date 2021/3/2
 */
public interface OrderTravelerDao {
    @Insert("insert into order_traveler values(#{orderId}, #{travelerId})")
    int save(@Param("orderId") String orderId, @Param("travelerId") String travelerId) throws Exception;
}
