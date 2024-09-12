package com.zb.service.impl;

import com.zb.entity.Case;
import com.zb.mapper.CaseMapper;
import com.zb.service.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.codec.StringDecoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@Service
public class CaseServiceimpl implements CaseService {


    @Autowired
    private CaseMapper caseMapper;

    //    private final Path rootLocation = Paths.get("uploaded-images");
    @Override
    public Case createCase(String title, String description) {

        caseMapper.add(title,description);
        return null;
    }
    @Override
    public String uploadImage(UUID caseId, MultipartFile file) {
        return null;
    }


}
