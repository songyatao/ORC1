package com.zb.service.impl;

import com.zb.entity.Casefile;
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
        return null;
    }


    @Override
    public List<?> getAll(int caseId) {
        return matchMapper.getAll(caseId);
    }


    @Override
    public void insert(int case_id, String file_path) {
        matchMapper.add(case_id, file_path);
    }

    @Override
    public void deleteByCaseId(int caseId) {
        matchMapper.deleteByCaseId(caseId);
    }

    @Override
    public List<String> getPathByCaseId(int case_id) {
        return matchMapper.getPathByCaseId(case_id);
    }


}
