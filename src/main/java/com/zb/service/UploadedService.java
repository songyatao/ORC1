package com.zb.service;

import com.zb.entity.Uploaded;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
public interface UploadedService {
    Uploaded createUpload(int caseId, String file_path);

    int findIdByPath(String path);

    int findIdByCaseIdAndName(int caseId, String name);
}
