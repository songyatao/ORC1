package com.zb.service.impl;

import com.zb.mapper.StrokeMapper;
import com.zb.service.StrokeService;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@Service
public class StrokeServiceimpl implements StrokeService {
    @Autowired
    private StrokeMapper strokeMapper;

    @Override
    public void insert(int case_id, String file_path, String file_name, int case_file_id) {
        strokeMapper.add(case_id, file_path, file_name, case_file_id);
    }

    @Override
    public List<String> getAllImageUrlsByUploadedId(int uploadedId) {
        return null;
    }

    @Override
    public List<?> getAll(int caseId) {
        return strokeMapper.getAll(caseId);
    }

    @Override
    public List<String> getCropsByCaseIdAndFileId(int case_id, int case_file_id) {
        return strokeMapper.getCropsByCaseIdAndFileId(case_id, case_file_id);
    }

    @Override
    public void deleteByCaseId(int caseId) {
        strokeMapper.deleteByCaseId(caseId);
    }
}
