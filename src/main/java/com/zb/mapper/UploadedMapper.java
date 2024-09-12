package com.zb.mapper;

import com.zb.entity.Uploaded;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@Mapper
public interface UploadedMapper {

    @Insert("insert into `uploaded`(case_id,file_path,uploaded_at,file_name) values (#{caseId}, #{file_path},NOW(),#{file_name})")
    int add(@Param("caseId") int caseId, @Param("file_path") String file_path,@Param("file_name") String file_name);//新增图片

    @Select("select id from uploaded where file_path = #{path}")
    int findIdByPath(@Param("path") String path);

    @Select("select id from uploaded where case_id = #{caseId} and file_name = #{name}")
    int findIdByCaseIdAndName(@Param("caseId") int caseId,@Param("name") String name);
}
