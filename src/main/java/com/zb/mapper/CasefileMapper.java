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
public interface CasefileMapper {
    @Insert("insert into `casefile`(file_name,file_path,case_id) values (#{file_name}, #{file_path},#{case_id})")
    int add(@Param("file_name") String file_name, @Param("file_path") String file_path, @Param("case_id") int case_id);//新增二维结果

    @Select("select id from `casefile` where file_name = #{file_name}")
    int getIdByName(@Param("file_name") String file_name);//根据uploaded_id查找

    @Select("select file_name from `casefile` where case_id = #{case_id}")
    List<String> getNames(@Param("case_id") int case_id);//根据uploaded_id查找

    @Select("select id from `casefile` where file_path = #{file_path}")
    int getIdByPath(@Param("file_path") String file_path);
}
