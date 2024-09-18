package com.zb.mapper;

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
public interface TwoMapper {
    @Insert("insert into `two`(uploaded_id,file_path) values (#{uploaded_id}, #{file_path})")
    int add(@Param("uploaded_id") int uploaded_id, @Param("file_path") String file_path);//新增二维结果


    @Select("select file_path from `two` where uploaded_id = #{uploaded_id}")
    List<String> getCropsByUploadedId(@Param("uploaded_id") int uploaded_id);//根据uploaded_id查找
}
