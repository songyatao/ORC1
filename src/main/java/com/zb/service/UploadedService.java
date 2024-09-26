package com.zb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zb.entity.Cases;
import com.zb.entity.Uploaded;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
public interface UploadedService extends IService<Uploaded> {
    Uploaded createUpload(int caseId, String file_path);

    int findIdByPath(String path);

    int findIdByCaseIdAndName(int caseId, String name);

    int findIdByCaseId(int caseId);

    String findFileNameByCaseId(int caseId);

    void deleteByCaseId(int caseId);

    int findCaseIdById(int id);

    List<String> findPathByCaseId(int case_id);

}
