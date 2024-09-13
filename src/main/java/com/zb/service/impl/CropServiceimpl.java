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
    public Crop createCrop(int uploaded_id, String file_path) {
        cropMapper.add(uploaded_id, file_path);
        return null;
    }

    @Override
    public List<String> getCropsByUploadedId(int uploaded_id) {
        return cropMapper.getCropsByUploadedId(uploaded_id);
    }
}
