package com.zb.service;


import org.apache.ibatis.annotations.Param;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */

public interface MatchService extends BaseService {
    void insert(int case_id, String file_path);

    void deleteByCaseId(int caseId);

}
