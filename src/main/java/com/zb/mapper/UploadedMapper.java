package com.zb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zb.entity.Cases;
import com.zb.entity.Uploaded;
import org.apache.ibatis.annotations.*;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@Mapper
public interface UploadedMapper extends BaseMapper<Uploaded> {

    @Insert("insert into `uploaded`(case_id,file_path,uploaded_at,file_name) values (#{caseId}, #{file_path},NOW(),#{file_name})")
    int add(@Param("caseId") int caseId, @Param("file_path") String file_path, @Param("file_name") String file_name);//新增图片

    @Select("select id from uploaded where file_path = #{path}")
    int findIdByPath(@Param("path") String path);

    @Select("select id from uploaded where case_id = #{caseId} and file_name = #{name}")
    int findIdByCaseIdAndName(@Param("caseId") int caseId, @Param("name") String name);

    @Select("select id from uploaded where case_id = #{caseId}")
    int findIdByCaseId(@Param("caseId") int caseId);

    @Select("select file_name from uploaded where case_id = #{caseId}")
    String findFileNameByCaseId(@Param("caseId") int caseId);

    @Delete("delete from `uploaded` where case_id = #{caseId}")
    void deleteByCaseId(@Param("caseId") int caseId);

    @Select("select case_id from `uploaded` where id = #{id}")
    int findCaseIdById(@Param("id") int id);


}
