package com.zb.mapper;

import com.zb.entity.Crop;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@Mapper
public interface CropMapper {
    @Insert("insert into `crop`(uploaded_id,file_path,file_name,case_file_id,case_id) values (#{uploaded_id}, #{file_path},#{file_name},#{case_file_id},#{case_id})")
    int add(@Param("uploaded_id") int uploaded_id, @Param("file_path") String file_path,@Param("file_name") String file_name,@Param("case_file_id") int case_file_id,@Param("case_id") int case_id);//新增剪裁结果


    @Select("select file_path from `crop` where uploaded_id = #{uploaded_id}")
    List<String> getCropsByUploadedId(@Param("uploaded_id") int uploaded_id);//根据uploaded_id查找

    @Select("select file_path from `crop` where case_id = #{case_id} and case_file_id = #{case_file_id}")
    List<String> getCropsByCaseIdAndFileId(@Param("case_id") int case_id,@Param("case_file_id") int case_file_id);//根据uploaded_id查找

    @Delete("delete from `crop` where uploaded_id = #{uploadedId}")
    void deleteByUploadedId(@Param("uploadedId") int uploadedId);

    @Delete("delete from `crop` where case_id = #{caseId}")
    void deleteByCaseId(@Param("caseId") int caseId);
}
