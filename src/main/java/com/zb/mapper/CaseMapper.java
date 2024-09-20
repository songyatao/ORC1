package com.zb.mapper;

import com.zb.entity.Case;
import org.apache.ibatis.annotations.Delete;
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
    void add(@Param("title") String title, @Param("description") String description);//新增案件

    //查找案件名称

    //根据id删除案件
    @Delete("delete from `case` where id = #{id}")
    void deleteById(@Param("id") String id);
}
