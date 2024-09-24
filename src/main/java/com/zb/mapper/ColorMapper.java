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
public interface ColorMapper {
    @Insert("insert into `color`(case_id,file_path,file_name,case_file_id) values (#{case_id}, #{file_path},#{file_name},#{case_file_id})")
    int add(@Param("case_id") int case_id, @Param("file_path") String file_path, @Param("file_name") String file_name, @Param("case_file_id") int case_file_id);//新增color结果

    @Select("select file_path from `color` where uploaded_id = #{uploaded_id}")
    List<String> getCropsByUploadedId(@Param("uploaded_id") int uploaded_id);//根据uploaded_id查找

    @Select("select file_path from `color` where case_id = #{case_id} and case_file_id = #{case_file_id}")
    List<String> getCropsByCaseIdAndFileId(@Param("case_id") int case_id,@Param("case_file_id") int case_file_id);
}
