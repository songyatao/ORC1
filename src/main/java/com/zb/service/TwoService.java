package com.zb.service;

import java.util.List;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
public interface TwoService extends BaseService{

    List<String> getCropsByCaseIdAndFileId(int case_id, int case_file_id);

    void deleteByCaseId(int caseId);
}
