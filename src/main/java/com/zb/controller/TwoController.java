package com.zb.controller;

import com.zb.Result.ResultBuilder;
import com.zb.entity.Casefile;
import com.zb.service.CasefileService;
import com.zb.service.TwoService;
import com.zb.service.UploadedService;
import com.zb.tools.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @auther 宋亚涛
 * @verson 1.0
 * 调用Two_Dimensional_recognition.py,对识别成功的进行二维处理，
 * 处理后的结果放在two_dimensional文件夹，并且放入数据库
 */
@RestController
@RequestMapping("/two")
@CrossOrigin//解决跨域问题
@Slf4j
public class TwoController {

    @Autowired
    private TwoService twoService;
    @Autowired
    private UploadedService uploadedService;
    @Autowired
    private CasefileService casefileService;

    //对识别成功的结果进行二维处理,增
    @ApiOperation("对识别成功的结果进行二维处理,增")
    @PostMapping("/add/{caseId}")
    public HttpResponse twoDimensional(@PathVariable("caseId") int caseId) {
        //每次点击数据库都会增加一次结果，逻辑不正确
        //如果已经有二维的结果了，则不再执行生成逻辑
        //根据caseId查询two数据库，如果为空，就执行，不为空就不执行
        List<?> twoFiles = twoService.getAll(caseId);
        if (twoFiles == null || twoFiles.isEmpty()) {
            BufferedReader in = null;

            try {
                CallTwo.Call(in, caseId);
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


            return DimensionTools.toDB(caseId, uploadedService, twoService, casefileService, "two_dimensional");
        } else {
            System.out.println("已经存在，不重复添加");
            return ResultBuilder.successNoData(ResultCode.SAVE_SUCCESS);
        }


    }


    //查
    @ApiOperation("将2维结果返回给前端，查")
    @RequestMapping("/load/{caseId}/{case_file_id}")
    public HttpResponse<List<String>> createFileNameButton(@PathVariable("caseId") int caseId,
                                                           @PathVariable("case_file_id") int id) {
        List<String> imageUrls = twoService.getCropsByCaseIdAndFileId(caseId, id);
        String baseUrl = "http://localhost:8080/";
        List<String> updatedPaths = imageUrls.stream()
                .map(path -> path.replace(AppRootPath.getappRootPath_static(), baseUrl)
                        .replace("\\", "/"))
                .collect(Collectors.toList());
        System.out.println(updatedPaths);
        return ResultBuilder.success(updatedPaths, ResultCode.QUERY_SUCCESS);
    }

}