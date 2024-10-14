package com.zb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zb.entity.Casefile;
import com.zb.entity.Cases;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@Mapper
public interface CasefileMapper extends BaseMapper<Casefile> {
    @Insert("insert into `casefile`(file_name,file_path,case_id,uploaded_id) values (#{file_name}, #{file_path},#{case_id},#{uploaded_id})")
    int add(@Param("file_name") String file_name, @Param("file_path") String file_path, @Param("case_id") int case_id, @Param("uploaded_id") int uploaded_id);//新增

    @Select("select id from `casefile` where file_name = #{file_name} and case_id = #{case_id}")
    int getIdByNameAndCaseId(@Param("file_name") String file_name,@Param("case_id") int case_id);//根据uploaded_id查找

    @Select("select file_name from `casefile` where case_id = #{case_id}")
    List<String> getNames(@Param("case_id") int case_id);//根据uploaded_id查找

    @Select("select id from `casefile` where file_path = #{file_path}")
    int getIdByPath(@Param("file_path") String file_path);

    @Delete("delete from `casefile` where uploaded_id = #{uploadedId}")
    void deleteByUploadedId(@Param("uploadedId") int uploadedId);

    @Delete("delete from `casefile` where case_id = #{caseId}")
    void deleteByCaseId(@Param("caseId") int caseId);
    @Select("select * from `casefile` where case_id = #{caseId}")
    List<Casefile> getAll(@Param("caseId") int caseId);
}
