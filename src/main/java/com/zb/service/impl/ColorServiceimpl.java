package com.zb.service.impl;

import com.zb.mapper.ColorMapper;
import com.zb.service.ColorService;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@Service
public class ColorServiceimpl implements ColorService {
    @Autowired
    private ColorMapper colorMapper;


    @Override
    public void insert(int case_id, String file_path, String file_name, int case_file_id) {
        colorMapper.add(case_id,file_path,file_name,case_file_id);
    }

    @Override
    public List<String> getAllImageUrlsByUploadedId(int uploadedId) {
        return colorMapper.getCropsByUploadedId(uploadedId);
    }

    @Override
    public List<String> getCropsByCaseIdAndFileId(int case_id, int case_file_id) {
        return colorMapper.getCropsByCaseIdAndFileId(case_id, case_file_id);
    }
}
