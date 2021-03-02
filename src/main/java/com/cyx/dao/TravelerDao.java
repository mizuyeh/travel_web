package com.cyx.dao;

import com.cyx.entity.Traveler;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description
 * @date 2021/2/27
 */
public interface TravelerDao {
    @Select("select * from traveler where id in (select travelerId from order_traveler where orderId=#{orderId})")
    List<Traveler> findById(String orderId) throws Exception;

    //通过旅客名查找id
    @Select("select id from traveler where travelerName = #{travelerName}")
    String findId(String travelerName) throws Exception;

    @Insert("insert into `traveler`(`travelerName`, `sex`, `phoneNum`, `credentialsType`, `credentialsNum`, `travelerType`) " +
            "values(#{travelerName}, #{sex}, #{phoneNum}, #{credentialsType}, #{credentialsNum}, #{travelerType})")
    int save(Traveler traveler) throws Exception;
}
