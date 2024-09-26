package com.zb.service.impl;

import com.zb.mapper.MatchMapper;
import com.zb.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@Service
public class MatchServiceimpl implements MatchService {
    @Autowired
    private MatchMapper matchMapper;


    @Override
    public void insert(int case_id, String file_path, String file_name, int case_file_id) {

    }

    @Override
    public List<String> getAllImageUrlsByUploadedId(int uploadedId) {
        return matchMapper.getCropsByUploadedId(uploadedId);
    }


    @Override
    public void insert(int case_id, String file_path) {
        matchMapper.add(case_id, file_path);
    }

    @Override
    public void deleteByCaseId(int caseId) {
        matchMapper.deleteByCaseId(caseId);
    }
}
