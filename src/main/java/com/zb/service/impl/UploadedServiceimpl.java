package com.zb.service.impl;

import com.zb.entity.Uploaded;
import com.zb.mapper.UploadedMapper;
import com.zb.service.UploadedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@Service
public class UploadedServiceimpl implements UploadedService {
    @Autowired
    private UploadedMapper uploadedMapper;

    @Override
    public Uploaded createUpload(int caseId, String file_path) {
        Path path = Paths.get(file_path);
        String fileNameWithExtension = path.getFileName().toString();
        String file_name = fileNameWithExtension.substring(0, fileNameWithExtension.lastIndexOf('.'));
        uploadedMapper.add(caseId, file_path, file_name);
        return null;
    }

    @Override
    public int findIdByPath(String path) {
        int id = uploadedMapper.findIdByPath(path);
        return id;
    }

    @Override
    public int findIdByCaseIdAndName(int caseId, String name) {
        return uploadedMapper.findIdByCaseIdAndName(caseId, name);
    }

    @Override
    public int findIdByCaseId(int caseId) {
        return uploadedMapper.findIdByCaseId(caseId);
    }

    @Override
    public String findFileNameByCaseId(int caseId) {
        return uploadedMapper.findFileNameByCaseId(caseId);
    }

}
