package com.zb.service.impl;

import com.zb.entity.Casefile;
import com.zb.mapper.HistogramMapper;
import com.zb.service.HistogramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@Service
public class HistogramServiceimpl implements HistogramService {
    @Autowired
    private HistogramMapper histogramMapper;


    @Override
    public void insert(int case_id, String file_path, String file_name, int case_file_id) {
        //没用
    }

    @Override
    public List<String> getAllImageUrlsByUploadedId(int uploadedId) {
        return histogramMapper.getCropsByUploadedId(uploadedId);
    }

    @Override
    public List<?> getAll(int caseId) {
        return histogramMapper.getAll(caseId);
    }


    @Override
    public void add(int case_id, String file_path) {
        histogramMapper.add(case_id, file_path);
    }

    @Override
    public List<String> getPathByCaseId(int case_id) {
        return histogramMapper.getPathByCaseId(case_id);
    }

    @Override
    public void deleteByCaseId(int caseId) {
        histogramMapper.deleteByCaseId(caseId);
    }
}
