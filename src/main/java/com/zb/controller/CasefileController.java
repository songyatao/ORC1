package com.zb.controller;

import com.zb.Result.ResultBuilder;
import com.zb.entity.Cases;
import com.zb.service.CasefileService;
import com.zb.tools.AppRootPath;
import com.zb.tools.HttpResponse;
import com.zb.tools.ResultCode;
import com.zb.tools.WordFileFinder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
@RestController
@RequestMapping("/casefile")
@CrossOrigin//解决跨域问题
@Slf4j
public class CasefileController {
    @Autowired
    CasefileService casefileService;

    @DeleteMapping("/delete/{id}")
    public HttpResponse delete(@PathVariable Integer id) {
        return null;
    }


    //点击“显示结果”按钮，把文件夹名字返回给前端，前端把这些名字做成按钮ok
    /*
            返回形式
            {
                "code": 20000,
                "message": "查询成功",
                "data": [
                    "00000",
                    "20273年1月1670",
                    "3年101670",
                    "3年1016m",
                    "李文龙"
                ]
            }

     */

    @RequestMapping("/{caseId}/load")
    public HttpResponse<List<String>> createFileNameButton(@PathVariable("caseId") int caseId) {
        List<String> fileNames = casefileService.getNames(caseId);
        System.out.println(fileNames);
        return ResultBuilder.success(fileNames, ResultCode.QUERY_SUCCESS);
    }

    //下载识别成功的word文档
    @GetMapping("/{caseId}/downloadWord")
    public ResponseEntity<InputStreamResource> downloadWord(@PathVariable("caseId") int caseId) {
        // 本地文件路径，请根据实际文件路径修改
        String filePath = AppRootPath.getappRootPath_result() + caseId + "\\picture";
        String result = WordFileFinder.findWordFile(filePath);

        System.out.println(result);
        try {
            // 创建文件对象
            File file = new File(result);

            // 创建文件输入流
            FileInputStream fis = new FileInputStream(file);

            // 设置响应头，指定文件名为"document.docx"
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=document.docx");

            // 返回文件的输入流资源
            return new ResponseEntity<>(new InputStreamResource(fis), headers, HttpStatus.OK);

        } catch (IOException e) {
            // 出现异常时返回500内部服务器错误
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
