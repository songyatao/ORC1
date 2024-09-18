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
    public void insert(int uploaded_id, String file_path) {
        colorMapper.add(uploaded_id, file_path);
    }

    @Override
    public List<String> getAllImageUrlsByUploadedId(int uploadedId) {
        return null;
    }
}
