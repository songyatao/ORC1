package com.zb.service.impl;

import com.zb.mapper.TwoMapper;
import com.zb.service.TwoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@Service("twoService")
public class TwoServiceimpl implements TwoService {
    @Autowired
    private TwoMapper twoMapper;


    @Override
    public void insert(int case_id, String file_path, String file_name, int case_file_id) {
        twoMapper.add(case_id, file_path, file_name, case_file_id);
    }

    @Override
    public List<String> getAllImageUrlsByUploadedId(int uploadedId) {
        return null;
    }


    @Override
    public List<String> getCropsByCaseIdAndFileId(int case_id, int case_file_id) {
        return twoMapper.getCropsByUploadedId(case_id, case_file_id);
    }
}
