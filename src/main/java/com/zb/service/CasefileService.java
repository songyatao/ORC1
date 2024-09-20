package com.zb.service;

import java.util.List;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
public interface CasefileService extends BaseService{

    void insert(String file_name, String file_path,int case_id);
    int getIdByName(String file_name);
    List<String> getNames(int case_id);
    int getIdByPath(String file_path);
}
