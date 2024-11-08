package com.zb.service.impl;

import com.zb.entity.Casefile;
import com.zb.mapper.ThreeMapper;
import com.zb.service.ThreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@Service("threeService")
public class ThreeServiceimpl implements ThreeService {
    @Autowired
    private ThreeMapper threeMapper;


    @Override
    public void insert(int case_id, String file_path, String file_name, int case_file_id) {
        threeMapper.add(case_id, file_path, file_name, case_file_id);
    }

    @Override
    public List<String> getAllImageUrlsByUploadedId(int uploaded_id) {
        return null;
    }

    @Override
    public List<?> getAll(int caseId) {
        return threeMapper.getAll(caseId);
    }


    @Override
    public List<String> getCropsByCaseIdAndFileId(int case_id, int case_file_id) {
        return threeMapper.getCropsByCaseIdAndFileId(case_id, case_file_id);
    }

    @Override
    public void deleteByCaseId(int caseId) {
        threeMapper.deleteByCaseId(caseId);
    }
}
