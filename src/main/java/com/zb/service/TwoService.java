package com.zb.service;

import com.zb.entity.Casefile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
public interface TwoService extends BaseService{

    List<String> getCropsByCaseIdAndFileId(int case_id, int case_file_id);

    void deleteByCaseId(int caseId);

}
