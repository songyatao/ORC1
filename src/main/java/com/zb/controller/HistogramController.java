package com.zb.controller;

import com.zb.Result.ResultBuilder;
import com.zb.service.HistogramService;
import com.zb.service.UploadedService;
import com.zb.tools.*;
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
@RequestMapping("/his")
@CrossOrigin//解决跨域问题
@Slf4j
public class HistogramController {
    @Autowired
    private HistogramService histogramService;
    @Autowired
    private UploadedService uploadedService;

    @PostMapping("/add/{caseId}")
    public HttpResponse his(@PathVariable("caseId") int caseId) {

        List<?> histogramFiles = histogramService.getAll(caseId);
        if (histogramFiles == null || histogramFiles.isEmpty()) {
            BufferedReader in = null;
            try {
                CallHistogram.Call(in, caseId);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            //结果加入数据库
            try {
                String file_path = AppRootPath.getappRootPath_result() + caseId + "\\image_statistics.png";
                histogramService.add(caseId, file_path);
                return ResultBuilder.successNoData(ResultCode.SAVE_SUCCESS);
            } catch (Exception e) {
                e.printStackTrace();
                return ResultBuilder.faile(ResultCode.CODE_ERROR);
            }
        } else {
            System.out.println("已经存在，不重复添加");
            return ResultBuilder.successNoData(ResultCode.SAVE_SUCCESS);
        }
    }

    //将his结果返回给前端
    @RequestMapping("/load/{caseId}")
    public HttpResponse<List<String>> getImages(@PathVariable int caseId) {
        List<String> imageUrls = histogramService.getPathByCaseId(caseId);
        String baseUrl = "http://localhost:8080/";
        List<String> updatedPaths = imageUrls.stream()
                .map(path -> path.replace(AppRootPath.getappRootPath_static(), baseUrl)
                        .replace("\\", "/"))
                .collect(Collectors.toList());
        System.out.println(updatedPaths);
        return ResultBuilder.success(updatedPaths, ResultCode.QUERY_SUCCESS);
    }

}
