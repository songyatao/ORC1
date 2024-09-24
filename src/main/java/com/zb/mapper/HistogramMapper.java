package com.zb.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@Mapper
public interface HistogramMapper {
    @Insert("insert into `histogram`(case_id,file_path) values (#{case_id}, #{file_path})")
    void add(@Param("case_id") int case_id, @Param("file_path") String file_path);//新增结果

    @Select("select file_path from `histogram` where uploaded_id = #{uploaded_id}")
    List<String> getCropsByUploadedId(@Param("uploaded_id") int uploaded_id);//根据uploaded_id查找

    @Select("select file_path from `histogram` where case_id = #{case_id}")
    List<String> getPathByCaseId(@Param("case_id") int case_id);

    @Delete("delete from `histogram` where case_id = #{caseId}")
    void deleteByCaseId(@Param("caseId") int caseId);
}
