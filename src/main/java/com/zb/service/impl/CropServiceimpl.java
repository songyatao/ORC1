package com.zb.service.impl;

import com.zb.entity.Crop;
import com.zb.mapper.CropMapper;
import com.zb.service.CropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@Service
public class CropServiceimpl implements CropService {
    @Autowired
    private CropMapper cropMapper;

    @Override
    public Crop createCrop(int uploaded_id, String file_path, String file_name,int case_file_id,int case_id) {
        cropMapper.add(uploaded_id, file_path, file_name,case_file_id,case_id);
        return null;
    }

    @Override
    public List<String> getCropsByUploadedId(int uploaded_id) {
        return cropMapper.getCropsByUploadedId(uploaded_id);
    }

    @Override
    public List<String> getCropsByCaseIdAndFileId(int case_id, int case_file_id) {
        return cropMapper.getCropsByCaseIdAndFileId(case_id,case_file_id);
    }
}
