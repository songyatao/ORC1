package com.zb.controller;

import com.zb.Result.ResultBuilder;
import com.zb.service.CasefileService;
import com.zb.service.ColorService;
import com.zb.service.UploadedService;
import com.zb.tools.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/color")
@CrossOrigin//解决跨域问题
@Slf4j
public class ColorController {
    @Autowired
    private UploadedService uploadedService;
    @Autowired
    private ColorService colorService;
    @Autowired
    private CasefileService casefileService;

    //增
    @PostMapping("/add/{caseId}")
    public HttpResponse color(@PathVariable("caseId") int caseId) {
        BufferedReader in = null;

        try {
            CallColor.Call(in, caseId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultBuilder.faile(ResultCode.CODE_ERROR);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return ResultBuilder.faile(ResultCode.CODE_ERROR);
                }
            }
        }


        return DimensionTools.toDB(caseId, uploadedService, colorService, casefileService, "rcolor");
    }


    //将结果返回给前端
    @RequestMapping("/load/{caseId}/{case_file_id}")
    public HttpResponse<List<String>> createFileNameButton(@PathVariable("caseId") int caseId,
                                                           @PathVariable("case_file_id") int id) {
        List<String> imageUrls = colorService.getCropsByCaseIdAndFileId(caseId, id);
        String baseUrl = "http://localhost:8080/";
        List<String> updatedPaths = imageUrls.stream()
                .map(path -> path.replace(AppRootPath.getappRootPath_static(), baseUrl)
                        .replace("\\", "/"))
                .collect(Collectors.toList());
        System.out.println(updatedPaths);
        return ResultBuilder.success(updatedPaths, ResultCode.QUERY_SUCCESS);
    }


}
