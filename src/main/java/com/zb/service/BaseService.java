package com.zb.service;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
public abstract interface BaseService {
    void insert(int case_id, String file_path, String file_name, int case_file_id);//新增二维结果


    List<String> getAllImageUrlsByUploadedId(int uploadedId);
}
