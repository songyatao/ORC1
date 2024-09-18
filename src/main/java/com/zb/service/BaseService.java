package com.zb.service;

import java.util.List;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
public abstract interface BaseService {
    void insert(int uploaded_id, String file_path);

    List<String> getAllImageUrlsByUploadedId(int uploadedId);
}
