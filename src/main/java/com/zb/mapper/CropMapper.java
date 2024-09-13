package com.zb.mapper;

import com.zb.entity.Crop;
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
public interface CropMapper {
    @Insert("insert into `crop`(uploaded_id,file_path) values (#{uploaded_id}, #{file_path})")
    int add(@Param("uploaded_id") int uploaded_id, @Param("file_path") String file_path);//新增剪裁结果


    @Select("select file_path from `crop` where uploaded_id = #{uploaded_id}")
    List<String> getCropsByUploadedId(@Param("uploaded_id") int uploaded_id);//根据uploaded_id查找
}
