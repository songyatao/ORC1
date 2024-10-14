package com.zb.service;


import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */

public interface MatchService extends BaseService {
    void insert(int case_id, String file_path);

    void deleteByCaseId(int caseId);
    List<String> getPathByCaseId(int case_id);

}
