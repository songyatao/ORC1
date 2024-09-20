package com.zb.service.impl;

import com.zb.mapper.CasefileMapper;
import com.zb.service.CasefileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@Service
public class CasefileServiceimpl implements CasefileService {
    @Autowired
    private CasefileMapper casefileMapper;


    @Override
    public void insert(int case_id, String file_path, String file_name, int case_file_id) {

    }

    @Override
    public List<String> getAllImageUrlsByUploadedId(int uploadedId) {
        return null;
    }


    @Override
    public void insert(String file_name, String file_path, int case_id) {
        casefileMapper.add(file_name, file_path, case_id);
    }

    @Override
    public int getIdByName(String file_name) {
        return casefileMapper.getIdByName(file_name);
    }

    @Override
    public List<String> getNames(int case_id) {
        return casefileMapper.getNames(case_id);
    }

    @Override
    public int getIdByPath(String file_path) {
        return casefileMapper.getIdByPath(file_path);
    }
}
