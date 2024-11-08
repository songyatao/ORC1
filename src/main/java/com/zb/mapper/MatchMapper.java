package com.zb.mapper;

import com.zb.entity.Two;
import org.apache.ibatis.annotations.*;
import org.python.apache.xerces.impl.xpath.regex.Match;

import java.util.List;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@Mapper
public interface MatchMapper {
    @Insert("insert into `match`(case_id,file_path) values (#{case_id}, #{file_path})")
    void add(@Param("case_id") int case_id, @Param("file_path") String file_path);//新增结果

    @Select("select file_path from `match` where case_id = #{case_id}")
    List<String> getPathByCaseId(@Param("case_id") int case_id);//根据uploaded_id查找

    @Delete("delete from `match` where case_id = #{caseId}")
    void deleteByCaseId(@Param("caseId") int caseId);

    @Select("select * from `match` where case_id = #{caseId}")
    List<Match> getAll(@Param("caseId") int caseId);
}
