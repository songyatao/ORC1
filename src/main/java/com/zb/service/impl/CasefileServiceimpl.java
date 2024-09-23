package com.zb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zb.entity.Casefile;
import com.zb.entity.Cases;
import com.zb.mapper.CaseMapper;
import com.zb.mapper.CasefileMapper;
import com.zb.service.CaseService;
import com.zb.service.CasefileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@Service
public class CasefileServiceimpl extends ServiceImpl<CasefileMapper, Casefile> implements CasefileService {
    @Autowired
    private CasefileMapper casefileMapper;

    @Override
    public Integer insert(String file_name, String file_path, int case_id, int uploaded_id) {
        Casefile casefile = new Casefile();
        casefile.setFile_name(file_name);
        casefile.setFile_path(file_path);
        casefile.setCase_id(case_id);
        casefile.setUploaded_id(uploaded_id);
        casefileMapper.insert(casefile);
        return casefile.getId();
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

    @Override
    public void deleteByUploadedId(int uploadedId) {
        casefileMapper.deleteByUploadedId(uploadedId);
    }

    @Override
    public void deleteByCaseId(int caseId) {
        casefileMapper.deleteByCaseId(caseId);
    }


}
