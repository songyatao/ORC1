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
    public void insert(int uploaded_id, String file_path) {
        twoMapper.add(uploaded_id, file_path);
    }

    @Override
    public List<String> getAllImageUrlsByUploadedId(int uploaded_id) {
        return  twoMapper.getCropsByUploadedId(uploaded_id);
    }
}
