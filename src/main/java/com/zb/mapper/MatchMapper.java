package com.zb.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@Mapper
public interface MatchMapper {
    @Insert("insert into `match`(case_id,file_path) values (#{case_id}, #{file_path})")
    void add(@Param("case_id") int case_id, @Param("file_path") String file_path);//新增结果

    @Select("select file_path from `match` where uploaded_id = #{uploaded_id}")
    List<String> getCropsByUploadedId(@Param("uploaded_id") int uploaded_id);//根据uploaded_id查找

    @Delete("delete from `match` where case_id = #{caseId}")
    void deleteByCaseId(@Param("caseId") int caseId);
}
