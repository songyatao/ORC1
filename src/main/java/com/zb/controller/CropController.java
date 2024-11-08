package com.zb.controller;

import com.zb.Result.ResultBuilder;
import com.zb.service.CropService;
import com.zb.tools.AppRootPath;
import com.zb.tools.HttpResponse;
import com.zb.tools.ResultCode;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */

@RestController
@RequestMapping("/crop")
@CrossOrigin//解决跨域问题
@Slf4j
public class CropController {
    @Autowired
    CropService cropService;

    //根据caseId和case_file_id 返回结果ok
    /*

                返回形式
                {
                "code": 20000,
                "message": "查询成功",
                "data": [
                    "http://localhost:8080/images/result/1/picture/00000/img4_00000.jpg",
                    "http://localhost:8080/images/result/1/picture/00000/img4_00000_1.jpg",
                    "http://localhost:8080/images/result/1/picture/00000/img4_00000_2.jpg",
                    "http://localhost:8080/images/result/1/picture/00000/img5_00000.jpg",
                    "http://localhost:8080/images/result/1/picture/00000/img5_00000_1.jpg",
                    "http://localhost:8080/images/result/1/picture/00000/img5_00000_2.jpg"
                ]
            }

     */
    @ApiOperation("根据caseId和case_file_id返回剪裁结果")
    @GetMapping("/{caseId}/{case_file_id}/load")
    public HttpResponse<List<String>> createFileNameButton(@PathVariable("caseId") int caseId,
                                                           @PathVariable("case_file_id") int id) throws InterruptedException {
        List<String> imageUrls = cropService.getCropsByCaseIdAndFileId(caseId, id);
        String baseUrl = "http://localhost:8080/";
        List<String> updatedPaths = imageUrls.stream()
                .map(path -> path.replace(AppRootPath.getappRootPath_static(), baseUrl)
                        .replace("\\", "/"))
                .collect(Collectors.toList());
        System.out.println(updatedPaths);
        Thread.sleep(2000);
        return ResultBuilder.success(updatedPaths, ResultCode.QUERY_SUCCESS);
    }


}
