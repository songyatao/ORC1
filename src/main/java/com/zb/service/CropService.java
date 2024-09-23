package com.zb.service;

import com.zb.entity.Crop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
public interface CropService {
    Crop createCrop(int uploaded_id, String file_path, String file_name, int case_file_id, int case_id);

    List<String> getCropsByUploadedId(int uploaded_id);

    List<String> getCropsByCaseIdAndFileId(int case_id, int case_file_id);

    void deleteByUploadedId(int uploadedId);

    void deleteByCaseId(int caseId);
}
