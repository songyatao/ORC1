package com.zb.service;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
public interface HistogramService extends BaseService {
    void add(int case_id, String file_path);//新增结果

    List<String> getPathByCaseId(int case_id);
}
