package com.zb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zb.entity.Cases;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@Mapper
public interface CaseMapper extends BaseMapper<Cases> {
    @Insert("insert into `cases`(title,description,created_at) values (#{title}, #{description},NOW())")
    void add(@Param("title") String title, @Param("description") String description);//新增

    @Select("select * from `cases`")
    List<Cases> getAll();
}
