package com.zb.service.impl;

import com.zb.mapper.FourMapper;
import com.zb.service.FourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@Service("fourService")
public class FourServiceimpl implements FourService {
    @Autowired
    private FourMapper fourMapper;
    @Override
    public void insert(int uploaded_id, String file_path) {
        fourMapper.add(uploaded_id, file_path);
    }
}
