package com.zb.mapper;

import com.zb.entity.Stroke;
import com.zb.entity.Three;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@Mapper
public interface StrokeMapper {
    @Insert("insert into `stroke`(case_id,file_path,file_name,case_file_id) values (#{case_id}, #{file_path},#{file_name},#{case_file_id})")
    int add(@Param("case_id") int case_id, @Param("file_path") String file_path, @Param("file_name") String file_name, @Param("case_file_id") int case_file_id);

    @Select("select file_path from `stroke` where case_id = #{case_id} and case_file_id = #{case_file_id}")
    List<String> getCropsByCaseIdAndFileId(@Param("case_id") int case_id, @Param("case_file_id") int case_file_id);

    @Delete("delete from `stroke` where case_id = #{caseId}")
    void deleteByCaseId(@Param("caseId") int caseId);

    @Select("select * from `stroke` where case_id = #{caseId}")
    List<Stroke> getAll(@Param("caseId") int caseId);
}
