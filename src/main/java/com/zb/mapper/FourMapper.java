package com.zb.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@Mapper
public interface FourMapper {
    @Insert("insert into `four`(uploaded_id,file_path) values (#{uploaded_id}, #{file_path})")
    int add(@Param("uploaded_id") int uploaded_id, @Param("file_path") String file_path);//新增四维结果
}
