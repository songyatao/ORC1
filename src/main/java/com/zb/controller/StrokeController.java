package com.zb.controller;

import com.zb.Result.ResultBuilder;
import com.zb.service.CasefileService;
import com.zb.service.StrokeService;
import com.zb.service.UploadedService;
import com.zb.tools.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@RestController
@RequestMapping("/stroke")
@CrossOrigin//解决跨域问题
@Slf4j
public class StrokeController {
    //HengShu.py
    @Autowired
    private StrokeService strokeService;
    @Autowired
    private UploadedService uploadedService;
    @Autowired
    private CasefileService casefileService;

    @ApiOperation("识别成功的结果进行笔画处理")
    @PostMapping("/add/{caseId}")
    public HttpResponse stroke(@PathVariable("caseId") int caseId) {

        List<?> strokeFiles = strokeService.getAll(caseId);
        if (strokeFiles == null || strokeFiles.isEmpty()) {
            BufferedReader in = null;

            try {
                CallStroke.Call(in, caseId);
            } catch (Exception e) {
                e.printStackTrace();
                return ResultBuilder.faile(ResultCode.CODE_ERROR);
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }


            //保存到数据库
            return DimensionTools.toDB(caseId, uploadedService, strokeService, casefileService, "stroke");
        } else {
            System.out.println("已经存在，不重复添加");
            return ResultBuilder.successNoData(ResultCode.SAVE_SUCCESS);
        }

    }

    @ApiOperation("返回给前端")
    @RequestMapping("/load/{caseId}/{case_file_id}")
    public HttpResponse<List<String>> createFileNameButton(@PathVariable("caseId") int caseId,
                                                           @PathVariable("case_file_id") int id) {
        List<String> imageUrls = strokeService.getCropsByCaseIdAndFileId(caseId, id);
        String baseUrl = "http://localhost:8080/";
        List<String> updatedPaths = imageUrls.stream()
                .map(path -> path.replace(AppRootPath.getappRootPath_static(), baseUrl)
                        .replace("\\", "/"))
                .collect(Collectors.toList());
        System.out.println(updatedPaths);
        return ResultBuilder.success(updatedPaths, ResultCode.QUERY_SUCCESS);
    }
}
