package com.zb.service.impl;

import com.zb.mapper.FourMapper;
import com.zb.service.FourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@Service("fourService")
public class FourServiceimpl implements FourService {
    @Autowired
    private FourMapper fourMapper;

    @Override
    public void insert(int case_id, String file_path, String file_name, int case_file_id) {

    }

    @Override
    public List<String> getAllImageUrlsByUploadedId(int uploadedId) {
        return fourMapper.getCropsByUploadedId(uploadedId);
    }
}
