package com.zb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zb.entity.Casefile;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
public interface CasefileService extends IService<Casefile> {

    Integer insert(String file_name, String file_path, int case_id, int uploaded_id);

    int getIdByNameAndCaseId(String file_name,int case_id);

    List<String> getNames(int case_id);

    int getIdByPath(String file_path);

    void deleteByUploadedId(int uploadedId);

    void deleteByCaseId(int caseId);

    List<Casefile> getAll(int caseId);
}
