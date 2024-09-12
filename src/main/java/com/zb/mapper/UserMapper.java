package com.zb.mapper;


import com.zb.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@Mapper
public interface UserMapper {
    //查询
    @Select("select * from user where username = #{uname}")
    User findByUser(@Param("uname") String uname);
    //新增
    @Insert("insert into user(username, password) values (#{uname}, #{psw})")
    void add(@Param("uname") String uname, @Param("psw") String psw);
}