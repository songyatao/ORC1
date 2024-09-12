package com.zb.service;

import com.zb.entity.Case;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
public interface CaseService {
     String uploadImage(UUID caseId, MultipartFile file);
    Case createCase(String title, String description);
}
