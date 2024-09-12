package com.zb.mapper;

import com.zb.entity.Case;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@Mapper
public interface CaseMapper {
    @Insert("insert into `case`(title,description,created_at) values (#{title}, #{description},NOW())")
    void add(@Param("title") String title,@Param("description") String description);//新增案件
}
