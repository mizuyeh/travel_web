package com.cyx.dao;

import com.cyx.entity.Member;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * @Description
 * @date 2021/2/27
 */
public interface MemberDao {
    @Select("select * from member where id=#{id}")
    Member findById(String id) throws Exception;

    //通过会员名查找id
    @Select("select id from member where memberName = #{memberName}")
    String findId(String memberName) throws Exception;

    @Insert("insert into member(memberName, nickname, phoneNum, email) " +
            "values(#{memberName}, #{nickname}, #{phoneNum}, #{email})")
    int save(Member member) throws Exception;
}
