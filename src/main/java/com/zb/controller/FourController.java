package com.zb.controller;

import com.zb.Result.ResultBuilder;
import com.zb.service.CasefileService;
import com.zb.service.FourService;
import com.zb.service.UploadedService;
import com.zb.tools.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
 */
@RestController
@RequestMapping("/four")
@CrossOrigin//解决跨域问题
@Slf4j
public class FourController {

    @Autowired
    private FourService fourService;
    @Autowired
    private UploadedService uploadedService;
    @Autowired
    private CasefileService casefileService;

    //对识别成功的结果进行四维处理,增
    @ApiOperation("对识别成功的结果进行四维处理,增")
    @PostMapping("/add/{caseId}")
    public HttpResponse fourDimensional(@PathVariable("caseId") int caseId) {

        List<?> fourFiles = fourService.getAll(caseId);
        if (fourFiles == null || fourFiles.isEmpty()) {
            BufferedReader in = null;
            Map<String, String> response = new HashMap<>();

            try {
                CallFour.Call(in, caseId);
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

            //将四维结果保存到数据库

            return DimensionTools.toDB(caseId, uploadedService, fourService, casefileService, "four_dimensional");
        } else {
            System.out.println("已经存在，不重复添加");
            return ResultBuilder.successNoData(ResultCode.SAVE_SUCCESS);
        }

    }

    //将四维结果返回给前端，查
    @ApiOperation("将四维结果返回给前端，查")
    @RequestMapping("/load/{caseId}/{case_file_id}")
    public HttpResponse<List<String>> createFileNameButton(@PathVariable("caseId") int caseId,
                                                           @PathVariable("case_file_id") int id) {
        List<String> imageUrls = fourService.getCropsByCaseIdAndFileId(caseId, id);
        String baseUrl = "http://localhost:8080/";
        List<String> updatedPaths = imageUrls.stream()
                .map(path -> path.replace(AppRootPath.getappRootPath_static(), baseUrl)
                        .replace("\\", "/"))
                .collect(Collectors.toList());
        System.out.println(updatedPaths);
        return ResultBuilder.success(updatedPaths, ResultCode.QUERY_SUCCESS);
    }
}
