package com.zb.service.impl;

import com.zb.mapper.ThreeMapper;
import com.zb.service.ThreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@Service
public class ThreeServiceimpl implements ThreeService {
    @Autowired
    private ThreeMapper threeMapper;
    @Override
    public void insertThree(int uploaded_id, String file_path) {
        threeMapper.add(uploaded_id,file_path);
    }
}
